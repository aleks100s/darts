package com.alextos.darts.android.game.in_game_history

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.GameHistoryView
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun InGameHistoryScreen(
    gameState: GameState,
    currentPage: Int,
    onNavigation: (InGameHistoryNavigationEvent) -> Unit
) {
    Screen(
        title = stringResource(id = R.string.history),
        onBackPressed = { onNavigation(InGameHistoryNavigationEvent.BackButtonPressed) }
    ) { modifier ->
        GameHistoryView(
            modifier = modifier,
            gameHistory = gameState.gameHistory,
            goal = gameState.gameGoal,
            currentPage = currentPage,
            onSelect = { turns, index -> onNavigation(InGameHistoryNavigationEvent.SelectTurn(turns, index)) }
        )
    }
}