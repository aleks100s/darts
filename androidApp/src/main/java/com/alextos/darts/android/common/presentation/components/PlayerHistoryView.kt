package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.Set

@Composable
fun PlayerHistoryView(
    playerHistory: PlayerHistory,
    onSelect: (Set) -> Unit
) {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            SectionHeader(title = playerHistory.player.name)
            PlayerHistoryHeader()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(playerHistory.turns) { set ->
                    SetItem(set = set) {
                        onSelect(set)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }
    }
}