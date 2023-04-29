package com.alextos.darts.android.game.in_game_history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.alextos.darts.android.common.presentation.views.GameHistoryView
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun InGameHistory(gameState: GameState, onSelect: (List<Turn>, Turn) -> Unit) {
    GameHistoryView(
        gameHistory = gameState.gameHistory,
        goal = gameState.gameGoal,
        currentPage = gameState.currentPage(),
        padding = PaddingValues(),
        onSelect = onSelect
    )
}