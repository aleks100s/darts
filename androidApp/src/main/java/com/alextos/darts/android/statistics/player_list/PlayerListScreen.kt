package com.alextos.darts.android.statistics.player_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alextos.darts.android.common.presentation.components.PlayerDisclosureItem
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.statistics.presentation.player_list.PlayerListEvent
import com.alextos.darts.statistics.presentation.player_list.PlayerListState

@Composable
fun PlayerListScreen(
    title: String,
    state: PlayerListState,
    onEvent: (PlayerListEvent) -> Unit
) {
    Screen(title = title) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.players) { player ->
                PlayerDisclosureItem(player = player) {
                    onEvent(PlayerListEvent.SelectPlayer(player))
                }
            }
        }
    }
}