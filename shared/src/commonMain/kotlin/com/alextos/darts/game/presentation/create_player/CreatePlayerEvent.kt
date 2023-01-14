package com.alextos.darts.game.presentation.create_player

sealed class CreatePlayerEvent {
    data class SavePlayer(val name: String): CreatePlayerEvent()
    data class ChangeNewPlayerName(val name: String): CreatePlayerEvent()
}
