package com.alextos.darts.android.game.create_game

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Start
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.FAB
import com.alextos.darts.android.common.presentation.components.PlayerItem
import com.alextos.darts.android.common.presentation.components.SectionHeader
import com.alextos.darts.core.domain.Player
import com.alextos.darts.game.presentation.create_game.CreateGameEvent
import com.alextos.darts.game.presentation.create_game.CreateGameState

@Composable
fun CreateGameScreen(
    state: CreateGameState,
    onEvent: (CreateGameEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FAB(
                text = stringResource(id = R.string.start_game),
                icon = Icons.Filled.Start,
                isEnabled = state.isReadyToCreateGame()
            ) {
                onEvent(CreateGameEvent.CreateGame)
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            SectionHeader(title = stringResource(id = R.string.select_players))

            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            onEvent(CreateGameEvent.CreatePlayer)
                        }) {
                            Text(
                                text = stringResource(id = R.string.add_new_player),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                items(state.allPlayers) { player ->
                    PlayerCheckbox(
                        player = player,
                        isChecked = state.isPlayerSelected(player),
                        onClick = {
                            onEvent(CreateGameEvent.SelectPlayer(it))
                        },
                        onLongClick = {
                            onEvent(CreateGameEvent.ShowDeletePlayerDialog(it))
                        }
                    )
                }
            }

            SectionHeader(title = stringResource(id = R.string.game_goal))

            GoalSelector(
                goals = state.goalOptions,
                selectedGoal = state.selectedGoal
            ) { goal ->
                onEvent(CreateGameEvent.SelectGoal(goal))
            }

            Spacer(modifier = Modifier.height(64.dp))
        }

        if (state.isDeletePlayerDialogShown) {
            DeletePlayerDialog(
                onClick = {
                    onEvent(CreateGameEvent.DeletePlayer)
                },
                onDismiss = {
                    onEvent(CreateGameEvent.HideDeletePlayerDialog)
                }
            )
        }
    }
}

@Composable
private fun DeletePlayerDialog(
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.delete_player))
        },
        text = {
            Text(
                text = stringResource(
                    id = R.string.this_cannot_be_undone
                )
            )
        },
        confirmButton = {
            Button(onClick = onClick) {
                Text(text = stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerCheckbox(
    player: Player,
    isChecked: Boolean,
    onClick: (Player) -> Unit,
    onLongClick: (Player) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onClick(player)
                },
                onLongClick = {
                    onLongClick(player)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PlayerItem(player = player, modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (isChecked) {
                Icons.Filled.CheckBox
            } else {
                Icons.Filled.CheckBoxOutlineBlank
            },
            contentDescription = "",
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
private fun GoalSelector(
    goals: List<Int>,
    selectedGoal: Int? = null,
    onClick: (Int) -> Unit
) {
    LazyRow(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        items(goals) {
            GoalOptionItem(
                goal = it,
                isSelected = selectedGoal == it
            ) {
                onClick(it)
            }
        }
    }
}

@Composable
fun GoalOptionItem(
    goal: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = goal.toString(),
        textAlign = TextAlign.Center
    )
}