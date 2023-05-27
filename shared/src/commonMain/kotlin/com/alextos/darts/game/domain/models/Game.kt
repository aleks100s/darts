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
    val finishWithDoubles: Boolean
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

    fun getUIDoublesString(): String {
        return if (finishWithDoubles) "✅ x2" else "❌ x2"
    }

    fun getUIGoalString(): String {
        return "🎯 $gameGoal"
    }

    fun getUIDurationString(): String {
        return "⏱ ${getDurationMinutesString()}:${getDurationSecondsString()}"
    }

    private fun getDurationMinutesString(): String {
        return if (duration.minutes > 9) "${duration.minutes}" else "0${duration.minutes}"
    }

    private fun getDurationSecondsString(): String {
        return if (duration.seconds > 9) "${duration.seconds}" else "0${duration.seconds}"
    }

    fun getUIWinnerString(): String {
        return "🏆 ${winner?.name}"
    }

    fun getPlayersListString(): String {
        return "\uD83D\uDC65 " + players.joinToString(", ") { it.name }
    }

    fun getFinishDateString(): String {
        return "${getDayString(finishTimestamp)} ${getTimeString(finishTimestamp)}"
    }

    fun getGameSettings(): GameSettings {
        return GameSettings(players, gameGoal, finishWithDoubles)
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
