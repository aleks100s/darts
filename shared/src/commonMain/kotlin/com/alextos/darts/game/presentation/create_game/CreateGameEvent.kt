package com.alextos.darts.game.presentation.create_game

import com.alextos.darts.core.domain.Player

sealed class CreateGameEvent {
    data class SelectPlayer(val player: Player): CreateGameEvent()
    data class SelectGoal(val option: Int): CreateGameEvent()
    object CreateGame: CreateGameEvent()
    object CreatePlayer: CreateGameEvent()
}