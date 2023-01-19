package com.alextos.darts.game.presentation.create_player

data class CreatePlayerState(
    val name: String = ""
) {
    fun isNameValid(): Boolean {
        return name.trim().length > 2
    }
}
