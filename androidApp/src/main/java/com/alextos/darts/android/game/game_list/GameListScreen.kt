package com.alextos.darts.android.game.game_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.FAB
import com.alextos.darts.android.common.presentation.components.PlayerCircle
import com.alextos.darts.android.common.presentation.extensions.getTitle
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.presentation.game_list.GameListEvent
import com.alextos.darts.game.presentation.game_list.GameListState

@Composable
fun GameListScreen(
    state: GameListState,
    onEvent: (GameListEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FAB(
                text = stringResource(id = R.string.create_game),
                icon = Icons.Filled.Create
            ) {
                onEvent(GameListEvent.CreateGame)
            }
        }
    ) { padding ->
        Screen(title = stringResource(id = R.string.games)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.games) {
                    GameItem(game = it) {
                        onEvent(GameListEvent.SelectGame(it))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }
    }
}

@Composable
private fun GameItem(
    game: Game,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = game.getTitle())
            Text(text = game.getDateString())
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(text = "\uD83C\uDFC6 ${game.winner?.name!!} \uD83C\uDFC6")
    }
}