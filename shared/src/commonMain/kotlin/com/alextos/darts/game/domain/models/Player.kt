package com.alextos.darts.game.domain.models

data class Player(
    val id: Long,
    val name: String
) {
    fun initials(): String {
        return name.first()
            .toString()
            .uppercase()
    }
}
