package com.alextos.darts.game.presentation.game

import com.alextos.darts.game.domain.models.PlayerHistory

data class GameState(
    val gameHistory: List<PlayerHistory> = listOf()
) {
}