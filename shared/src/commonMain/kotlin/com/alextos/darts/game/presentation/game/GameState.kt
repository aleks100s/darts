package com.alextos.darts.game.presentation.game

import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.players.domain.models.Player

data class GameState(
    val gameHistory: List<PlayerHistory> = listOf(),
    val currentPlayer: Player? = null,
    val isGameFinished: Boolean = false
) {
    fun currentPage(): Int = currentPlayer?.let { gameHistory.map { it.player }.indexOf(it) } ?: 0
}