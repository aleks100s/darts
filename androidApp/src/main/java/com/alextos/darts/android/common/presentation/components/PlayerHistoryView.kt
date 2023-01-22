package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.Set

@Composable
fun PlayerHistoryScreen(playerHistory: PlayerHistory, onSelect: (Set) -> Unit) {
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                SectionHeader(title = playerHistory.player.name)
                PlayerHistoryHeader()
            }
            items(playerHistory.turns) { set ->
                SetItem(set = set) {
                    onSelect(set)
                }
            }
        }
    }
}