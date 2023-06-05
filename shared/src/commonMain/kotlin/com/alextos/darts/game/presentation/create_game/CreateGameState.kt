package com.alextos.darts.game.presentation.create_game

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.models.GameSettings

data class CreateGameState(
    val allPlayers: List<Player> = listOf(),
    val selectedPlayers: List<Player> = listOf(),
    val selectedGoal: Int? = null,
    val goalOptions: List<Int> = listOf(101, 301, 501),
    val playerToDelete: Player? = null,
    val isDeletePlayerDialogShown: Boolean = false,
    val isFinishWithDoublesChecked: Boolean = false,
    val isRandomPlayersOrderChecked: Boolean = false,
    val isStatisticsDisabled: Boolean = false
) {
    fun isReadyToCreateGame() = selectedPlayers.isNotEmpty() && selectedGoal != null

    fun isPlayerSelected(player: Player): Boolean = selectedPlayers.contains(player)

    fun isGoalSelected(goal: Int): Boolean = selectedGoal == goal

    fun getSettings(): GameSettings = GameSettings(
        selectedPlayers,
        selectedGoal ?: 0,
        isFinishWithDoublesChecked,
        isRandomPlayersOrderChecked,
        isStatisticsDisabled
    )
}