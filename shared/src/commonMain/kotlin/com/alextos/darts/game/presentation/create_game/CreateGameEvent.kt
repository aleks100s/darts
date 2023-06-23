package com.alextos.darts.game.presentation.create_game

import com.alextos.darts.core.domain.model.Player

sealed class CreateGameEvent {
    data class SelectPlayer(val player: Player): CreateGameEvent()
    data class SelectGoal(val option: Int): CreateGameEvent()
    data class ShowDeletePlayerDialog(val player: Player): CreateGameEvent()
    data class ToggleFinishWithDoubles(val isChecked: Boolean): CreateGameEvent()
    data class ToggleTurnLimit(val isChecked: Boolean): CreateGameEvent()
    data class ToggleRandomPlayersOrder(val isChecked: Boolean): CreateGameEvent()
    data class ToggleDisableStatistics(val isChecked: Boolean): CreateGameEvent()
    object CreateGame: CreateGameEvent()
    object CreatePlayer: CreateGameEvent()
    object HideDeletePlayerDialog: CreateGameEvent()
    object DeletePlayer: CreateGameEvent()
}