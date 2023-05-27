package com.alextos.darts.android.game.game_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.FAB
import com.alextos.darts.android.common.presentation.extensions.getTitle
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
    onBackPressed: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (!state.isLoading) {
                FAB(
                    text = stringResource(id = R.string.create_game),
                    icon = Icons.Filled.Create
                ) {
                    onEvent(GameListEvent.CreateGame)
                }
            }
        }
    ) { padding ->
        Screen(
            title = stringResource(id = R.string.games),
            isBackButtonVisible = false,
            onBackPressed = onBackPressed
        ) { modifier ->
            if (state.isLoading) {
                LoadingView()
            } else if (state.games.isEmpty()) {
                NoDataView(modifier)
            } else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.games) {
                        GameItem(
                            game = it,
                            onClick = {
                                onEvent(GameListEvent.SelectGame(it))
                            },
                            onLongClick = {
                                onEvent(GameListEvent.ShowActionsDialog(it))
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(72.dp))
                    }
                }
            }
        }

        if (state.isActionsDialogShown) {
            GameActionsDialog(
                title = state.selectedGame?.getTitle() ?: "",
                onDismiss = { onEvent(GameListEvent.HideActionsDialog) },
                onReplay = {
                    state.selectedGame?.let {
                        onEvent(GameListEvent.ReplayGame(it))
                    }
                },
                onDelete = {
                    state.selectedGame?.let {
                        onEvent(GameListEvent.ShowDeleteGameDialog(it))
                    }
                }
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

@Composable
private fun GameActionsDialog(
    title: String,
    onDismiss: () -> Unit,
    onReplay: () -> Unit,
    onDelete: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onReplay) {
                Text(text = stringResource(id = R.string.replay))
            }

            Button(onClick = onDelete) {
                Text(text = stringResource(id = R.string.delete))
            }

            Button(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    }
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
        Column {
            Text(text = game.getTitle())
            Text(text = game.getFinishDateString())
        }

        Spacer(modifier = Modifier.weight(1f))

        game.winner?.let { winner ->
            Text(text = "\uD83C\uDFC6 ${winner.name} \uD83C\uDFC6")
        }
    }
}