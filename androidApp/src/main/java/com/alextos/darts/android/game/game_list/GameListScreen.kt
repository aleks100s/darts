package com.alextos.darts.android.game.game_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.BuildConfig
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.Chevron
import com.alextos.darts.android.common.presentation.components.CustomDialog
import com.alextos.darts.android.common.presentation.components.FAB
import com.alextos.darts.android.common.presentation.components.RoundedView
import com.alextos.darts.android.common.presentation.extensions.getTitle
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.LoadingView
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.presentation.game_list.GameListEvent
import com.alextos.darts.game.presentation.game_list.GameListState

@Composable
fun GameListScreen(
    state: GameListState,
    onEvent: (GameListEvent) -> Unit,
    onNavigation: (GameListNavigationEvent) -> Unit,
    populateDB: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (!state.isLoading) {
                FAB(
                    text = stringResource(id = R.string.create_game),
                    icon = Icons.Filled.Create
                ) {
                    onNavigation(GameListNavigationEvent.CreateGame)
                }
            }
        }
    ) { padding ->
        Screen(
            title = stringResource(id = R.string.games),
            isBackButtonVisible = false,
            onBackPressed = { onNavigation(GameListNavigationEvent.BackButtonPressed) },
            additionalNavBarContent = {
                Button(
                    onClick = { onNavigation(GameListNavigationEvent.ShowCalculator) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.8f),
                        contentColor = MaterialTheme.colors.onSecondary
                    )
                ) {
                    Text(stringResource(id = R.string.calculator))
                }
            }
        ) { modifier ->
            if (state.isLoading) {
                LoadingView()
            } else if (state.isEmpty) {
                Column {
                    if (BuildConfig.DEBUG) {
                        Button(onClick = populateDB) {
                            Text("Populate DB")
                        }
                    }
                    NoDataView(modifier)
                }
            } else {
                RoundedView(modifier.padding(padding)) {
                    LazyColumn(modifier = Modifier.surfaceBackground()) {
                        if (state.pausedGames.isNotEmpty()) {
                            gamesList(
                                games = state.pausedGames,
                                isOngoing = true,
                                onEvent = onEvent,
                                onNavigation = onNavigation
                            )
                        }
                        if (state.finishedGames.isNotEmpty()) {
                            gamesList(
                                games = state.finishedGames,
                                isOngoing = false,
                                onEvent = onEvent,
                                onNavigation = onNavigation
                            )
                        }
                    }
                }
            }
        }

        if (state.isActionsDialogShown) {
            GameActionsDialog(
                state = state,
                onEvent = onEvent,
                onNavigation = onNavigation
            )
        }

        if (state.isDeleteGameDialogShown) {
            DeleteGameDialog(
                onClick = { onEvent(GameListEvent.DeleteGame) },
                onDismiss = { onEvent(GameListEvent.HideDeleteGameDialog) }
            )
        }
    }
}

private fun LazyListScope.gamesList(
    games: List<Game>,
    isOngoing: Boolean,
    onEvent: (GameListEvent) -> Unit,
    onNavigation: (GameListNavigationEvent) -> Unit
) {
    itemsIndexed(games) { index, game ->
        GameItem(
            game = game,
            onClick = {
                if (isOngoing) {
                    onNavigation(GameListNavigationEvent.ResumeGame(game))
                } else {
                    onNavigation(GameListNavigationEvent.SelectGame(game))
                }
            },
            onLongClick = {
                onEvent(GameListEvent.ShowActionsDialog(game))
            }
        )
        if (index != games.lastIndex) {
            Divider(startIndent = 16.dp)
        }
    }
}

@Composable
private fun GameActionsDialog(
    state: GameListState,
    onEvent: (GameListEvent) -> Unit,
    onNavigation: (GameListNavigationEvent) -> Unit
) {
    val defaultActionTitle = if (state.selectedGame?.isPaused == true) {
        stringResource(id = R.string.continue_game)
    } else {
        stringResource(id = R.string.replay)
    }
    val defaultAction: () -> Unit = if (state.selectedGame?.isPaused == true) {
        {
            state.selectedGame?.let {
                onEvent(GameListEvent.ResumeGame)
                // TODO: continue the game
            }
        }
    } else {
        {
            state.selectedGame?.let {
                onEvent(GameListEvent.ReplayGame)
                onNavigation(GameListNavigationEvent.ReplayGame(it))
            }
        }
    }
    CustomDialog(
        title = state.selectedGame?.getTitle() ?: "",
        defaultActionTitle = defaultActionTitle,
        defaultAction = defaultAction,
        destructiveActionTitle = stringResource(id = R.string.delete),
        destructiveAction = {
            state.selectedGame?.let {
                onEvent(GameListEvent.ShowDeleteGameDialog(it))
            }
        },
        cancelActionTitle = stringResource(id = R.string.cancel),
        cancelAction = {
            onEvent(GameListEvent.HideActionsDialog)
        }
    )
}

@Composable
private fun DeleteGameDialog(
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.delete_game))
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
private fun GameItem(
    game: Game,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val bgColor = if (game.isPaused) {
        Color.Yellow.copy(alpha = 0.5f)
    } else {
        Color.Transparent
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    onLongClick()
                }
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = game.getTitle(),
                style = MaterialTheme.typography.caption
            )

            Text(text = game.getPlayersListString())

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val parts = game.getUITitleStringParts()
                for (part in parts) {
                    Text(text = part)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Chevron(contentDescription = stringResource(id = R.string.open_game))
    }
}