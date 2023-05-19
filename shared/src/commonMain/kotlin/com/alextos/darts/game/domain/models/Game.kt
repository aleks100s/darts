package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
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

    fun getGameSettings(): GameSettings {
        return GameSettings(players, gameGoal, false)
    }

    internal fun getDayString(): String {
        return "${timestamp.dayOfMonth}.${timestamp.monthNumber}.${timestamp.year}"
    }

    internal fun getTimeString(): String {
        return "${getHourString()}:${getMinuteString()}"
    }

    internal fun getHourString(): String {
        return if (timestamp.hour > 9) "${timestamp.hour}" else "0${timestamp.hour}"
    }

    internal fun getMinuteString(): String {
        return if (timestamp.minute > 9) "${timestamp.minute}" else "0${timestamp.minute}"
    }
}
