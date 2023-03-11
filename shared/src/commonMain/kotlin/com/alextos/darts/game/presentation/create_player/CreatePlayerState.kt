package com.alextos.darts.game.presentation.create_player

import com.alextos.darts.core.domain.Player

data class CreatePlayerState(
    val name: String = "",
    val allPlayers: List<Player> = listOf()
 ) {
    fun isNameValid(): Boolean {
        return name.trim().length > 2 && !allPlayers.map { it.name }.contains(name.trim())
    }
}
