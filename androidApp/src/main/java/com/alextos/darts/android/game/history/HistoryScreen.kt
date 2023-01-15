package com.alextos.darts.android.game.history

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.alextos.darts.android.common.presentation.components.GameHistoryScreen
import com.alextos.darts.game.domain.models.PlayerHistory

@Composable
fun HistoryScreen(history: List<PlayerHistory>) {
    Scaffold {
        GameHistoryScreen(
            gameHistory = history,
            currentPage = 0,
            padding = it
        )
    }
}