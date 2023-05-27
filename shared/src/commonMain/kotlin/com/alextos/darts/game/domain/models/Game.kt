package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Long? = null,
    val players: List<Player>,
    var winner: Player? = null,
    val gameGoal: Int,
    val finishTimestamp: LocalDateTime,
    val startTimestamp: LocalDateTime?
) {
    val duration: GameDuration
        get() {
            if (startTimestamp == null) {
                return GameDuration(0, 0)
            }
            val difference = finishTimestamp.toInstant(TimeZone.currentSystemDefault()) - startTimestamp.toInstant(TimeZone.currentSystemDefault())
            val secondsDifference = difference.inWholeSeconds
            val minutes = secondsDifference / 60
            val seconds = secondsDifference % 60
            return GameDuration(minutes.toInt(), seconds.toInt())
        }

    fun getFinishDateString(): String {
        return "${getDayString(finishTimestamp)} ${getTimeString(finishTimestamp)}"
    }

    fun getGameSettings(): GameSettings {
        return GameSettings(players, gameGoal, false)
    }

    internal fun getDayString(timestamp: LocalDateTime): String {
        return "${timestamp.dayOfMonth}.${timestamp.monthNumber}.${timestamp.year}"
    }

    internal fun getTimeString(timestamp: LocalDateTime): String {
        return "${getHourString(timestamp)}:${getMinuteString(timestamp)}"
    }

    internal fun getHourString(timestamp: LocalDateTime): String {
        return if (timestamp.hour > 9) "${timestamp.hour}" else "0${timestamp.hour}"
    }

    internal fun getMinuteString(timestamp: LocalDateTime): String {
        return if (timestamp.minute > 9) "${timestamp.minute}" else "0${timestamp.minute}"
    }
}
