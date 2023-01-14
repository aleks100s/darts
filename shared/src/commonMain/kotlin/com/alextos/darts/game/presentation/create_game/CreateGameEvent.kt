package com.alextos.darts.game.presentation.create_game

import com.alextos.darts.players.domain.models.Player

sealed class CreateGameEvent {
    data class SelectPlayer(val player: Player): CreateGameEvent()
    data class DeselectPlayer(val player: Player): CreateGameEvent()
    data class CreateGame(val players: List<Player>, val goal: Int): CreateGameEvent()
    object CreatePlayer: CreateGameEvent()
}