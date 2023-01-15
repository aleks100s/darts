package com.alextos.darts.android.game.game.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.runtime.Composable
import com.alextos.darts.android.common.presentation.FAB
import com.alextos.darts.android.common.presentation.components.GameHistoryScreen
import com.alextos.darts.game.domain.models.Sector
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun GameScreen(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    BackHandler(true) {}
    Scaffold(
        floatingActionButton = {
            FAB(text = "Shot", icon = Icons.Filled.Dashboard) {
                onEvent(GameEvent.MakeShot(Sector.Double11))
            }
        }
    ) {
        GameHistoryScreen(
            gameHistory = state.gameHistory,
            currentPage = state.currentPage(),
            padding = it
        )
    }
}