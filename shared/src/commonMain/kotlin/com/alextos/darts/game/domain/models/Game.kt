package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.Player
import kotlinx.datetime.LocalDateTime

data class Game(
    val id: Long? = null,
    val players: List<Player>,
    var winner: Player? = null,
    val gameGoal: Int,
    val timestamp: LocalDateTime
) {
    fun getDateString(): String {
        return "${timestamp.dayOfMonth}.${timestamp.monthNumber}.${timestamp.year}"
    }
}
