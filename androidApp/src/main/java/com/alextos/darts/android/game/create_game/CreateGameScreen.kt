package com.alextos.darts.android.game.create_game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Start
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.FAB
import com.alextos.darts.android.game.create_game.components.GoalSelector
import com.alextos.darts.android.game.create_game.components.PlayerItem
import com.alextos.darts.android.common.presentation.components.SectionHeader
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SectionHeader(title = stringResource(id = R.string.selected_players))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                items(state.selectedPlayers) { player ->
                    PlayerItem(player = player) {
                        onEvent(CreateGameEvent.DeselectPlayer(player))
                    }
                }
            }

            SectionHeader(title = stringResource(id = R.string.all_players))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
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
                    PlayerItem(player = player) {
                        onEvent(CreateGameEvent.SelectPlayer(player))
                    }
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
    }
}