package com.alextos.darts.game.presentation.create_game

import com.alextos.darts.core.domain.Player

data class CreateGameState(
    val allPlayers: List<Player> = listOf(),
    val selectedPlayers: List<Player> = listOf(),
    val selectedGoal: Int? = null,
    val goalOptions: List<Int> = listOf(101, 301, 501)
) {
    fun isReadyToCreateGame() = selectedPlayers.count() > 1 && selectedGoal != null
}