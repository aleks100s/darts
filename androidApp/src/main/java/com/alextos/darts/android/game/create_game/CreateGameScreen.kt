package com.alextos.darts.android.game.create_game

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.FAB
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                SectionHeader(title = stringResource(id = R.string.select_players))
            }

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
                    isChecked = state.selectedPlayers.contains(player),
                ) {
                    onEvent(CreateGameEvent.SelectPlayer(player))
                }
            }

            item {
                SectionHeader(title = stringResource(id = R.string.game_goal))
            }

            item {
                GoalSelector(
                    goals = state.goalOptions,
                    selectedGoal = state.selectedGoal
                ) { goal ->
                    onEvent(CreateGameEvent.SelectGoal(goal))
                }
            }

            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
private fun PlayerCheckbox(
    player: Player,
    isChecked: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PlayerItem(player = player)
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
fun PlayerItem(player: Player) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val color = MaterialTheme.colors.primary
        Text(
            text = player.initials(),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .padding(16.dp)
                .drawBehind {
                    drawCircle(
                        color = color,
                        radius = this.size.maxDimension
                    )
                }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = player.name)
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