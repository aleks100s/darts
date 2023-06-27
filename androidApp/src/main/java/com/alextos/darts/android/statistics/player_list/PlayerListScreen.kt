package com.alextos.darts.android.statistics.player_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alextos.darts.android.common.presentation.components.PlayerDisclosureItem
import com.alextos.darts.android.common.presentation.components.RoundedView
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.LoadingView
import com.alextos.darts.android.common.presentation.views.NoDataView
import com.alextos.darts.statistics.presentation.player_list.PlayerListState

@Composable
fun PlayerListScreen(
    title: String,
    state: PlayerListState,
    onNavigationEvent: (PlayerListNavigationEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    Screen(title = title, onBackPressed = onBackPressed) { modifier ->
        if (state.isLoading) {
            LoadingView()
        } else if (state.players.isEmpty()) {
            NoDataView(modifier = modifier)
        } else {
            RoundedView(modifier) {
                LazyColumn(modifier = Modifier.surfaceBackground()) {
                    items(state.players) { player ->
                        PlayerDisclosureItem(
                            player = player,
                            showDivider = state.players.last() != player
                        ) {
                            onNavigationEvent(PlayerListNavigationEvent.SelectNavigationPlayer(player))
                        }
                    }
                }
            }
        }
    }
}