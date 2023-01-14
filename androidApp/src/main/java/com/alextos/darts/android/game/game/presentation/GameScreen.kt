package com.alextos.darts.android.game.game.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameState

@Composable
fun GameScreen(
    state: GameState,
    onEvent: (GameEvent) -> Unit
) {
    Text(text = "Game Screen")
}