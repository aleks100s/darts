package com.alextos.darts.android.game.in_game_history

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.GameHistoryView
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun InGameHistoryScreen(
    gameState: GameState,
    onSelect: (List<Turn>, Int) -> Unit,
    onBackPressed: () -> Unit
) {
    Screen(
        title = stringResource(id = R.string.history),
        onBackPressed = onBackPressed
    ) { modifier ->
        GameHistoryView(
            modifier = modifier,
            gameHistory = gameState.gameHistory,
            goal = gameState.gameGoal,
            currentPage = gameState.currentPage(),
            onSelect = onSelect
        )
    }
}