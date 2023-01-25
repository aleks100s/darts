package com.alextos.darts.android.statistics.player_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.PlayerDisclosureItem
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.game.create_game.components.PlayerItem
import com.alextos.darts.statistics.presentation.player_list.PlayerListEvent
import com.alextos.darts.statistics.presentation.player_list.PlayerListState

@Composable
fun PlayerListScreen(
    state: PlayerListState,
    onEvent: (PlayerListEvent) -> Unit
) {
    Screen(title = stringResource(id = R.string.shot_distribution_statistics)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.players) { player ->
                PlayerDisclosureItem(player = player) {
                    onEvent(PlayerListEvent.SelectPlayer(player))
                }
            }
        }
    }
}