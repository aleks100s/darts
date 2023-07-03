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
    val startTimestamp: LocalDateTime?,
    val isWinWithDoublesEnabled: Boolean,
    val isRandomPlayerOrderEnabled: Boolean,
    val isStatisticsEnabled: Boolean,
    val isTurnLimitEnabled: Boolean,
    val isOngoing: Boolean
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

    fun getUITitleStringParts(): List<String> {
        val parts = mutableListOf<String>()
        parts.add("🎯 $gameGoal")
        if (!duration.isEmpty) {
            parts.add("⏱ ${getDurationMinutesString()}:${getDurationSecondsString()}")
        }
        if (winner != null) {
            parts.add("🏆 ${winner?.name}")
        }
        if (isWinWithDoublesEnabled) {
            parts.add("\uD83C\uDFC1 x2")
        }
        if (isRandomPlayerOrderEnabled) {
            parts.add("\uD83C\uDFB2")
        }
        if (isStatisticsEnabled) {
            parts.add("\uD83D\uDCCA")
        }
        return parts
    }

    private fun getDurationMinutesString(): String {
        return if (duration.minutes > 9) "${duration.minutes}" else "0${duration.minutes}"
    }

    private fun getDurationSecondsString(): String {
        return if (duration.seconds > 9) "${duration.seconds}" else "0${duration.seconds}"
    }

    fun getPlayersListString(): String {
        return if (players.isEmpty()) {
            ""
        } else if (players.size == 1) {
            "\uD83D\uDC64 ${players[0].name}"
        } else {
            "\uD83D\uDC65 " + players.joinToString(", ") { it.name }
        }
    }

    fun getFinishDateString(): String {
        return "${getDayString(finishTimestamp)} ${getTimeString(finishTimestamp)}"
    }

    fun getGameSettings(): GameSettings {
        return GameSettings(players, gameGoal, isWinWithDoublesEnabled, isRandomPlayerOrderEnabled, isStatisticsEnabled, isTurnLimitEnabled)
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
