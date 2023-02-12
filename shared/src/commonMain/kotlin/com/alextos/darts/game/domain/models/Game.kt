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
        return "${getDayString()} ${getTimeString()}"
    }

    private fun getDayString(): String {
        return "${timestamp.dayOfMonth}.${timestamp.monthNumber}.${timestamp.year}"
    }

    private fun getTimeString(): String {
        return "${getHourString()}:${getMinuteString()}"
    }

    private fun getHourString(): String {
        return if (timestamp.hour > 9) "${timestamp.hour}" else "0${timestamp.hour}"
    }

    private fun getMinuteString(): String {
        return if (timestamp.minute > 9) "${timestamp.minute}" else "0${timestamp.minute}"
    }
}
