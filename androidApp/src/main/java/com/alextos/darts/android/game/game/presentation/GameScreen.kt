package com.alextos.darts.android.game.game.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.alextos.darts.android.game.game.presentation.components.GameHistoryScreen
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun GameScreen(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    BackHandler(true) {}

    GameHistoryScreen(
        gameHistory = state.gameHistory,
        currentPage = state.currentPage()
    ) {
        onEvent(GameEvent.MakeShot(it))
    }
}