package com.alextos.darts.android.game.game.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.game.create_game.presentation.components.SectionHeader
import com.alextos.darts.android.game.game.presentation.components.SetItem
import com.alextos.darts.game.domain.models.PlayerHistory

@Composable
fun PlayerHistoryScreen(playerHistory: PlayerHistory) {
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
                SetItem(set = set)
            }
        }
    }
}