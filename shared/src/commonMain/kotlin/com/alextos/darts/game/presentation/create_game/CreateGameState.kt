package com.alextos.darts.game.presentation.create_game

import com.alextos.darts.core.domain.Player

data class CreateGameState(
    val allPlayers: List<Player> = listOf(),
    val selectedPlayers: List<Player> = listOf(),
    val selectedGoal: Int? = null,
    val goalOptions: List<Int> = listOf(101, 301, 501),
    val playerToDelete: Player? = null,
    val isDeletePlayerDialogShown: Boolean = false
) {
    fun isReadyToCreateGame() = selectedPlayers.isNotEmpty() && selectedGoal != null

    fun isPlayerSelected(player: Player): Boolean = selectedPlayers.contains(player)

    fun isGoalSelected(goal: Int): Boolean = selectedGoal == goal
}