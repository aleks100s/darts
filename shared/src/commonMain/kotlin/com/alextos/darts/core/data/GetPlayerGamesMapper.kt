package com.alextos.darts.core.data

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.models.Game
import database.GetPlayerGames
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun List<GetPlayerGames>.mapToGames(): List<Game> {
    return this.groupBy { it.gameId }
        .entries
        .map { (_, group)  ->
            val players = group.map { Player(it.playerId, it.playerName) }
            return@map Game(
                id = group.firstOrNull()?.gameId,
                players = players,
                winner = null,
                gameGoal = 0,
                finishTimestamp = Instant.fromEpochMilliseconds(0)
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                startTimestamp = Instant.fromEpochMilliseconds(0)
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                isWinWithDoublesEnabled = false,
                isRandomPlayerOrderEnabled = false,
                isStatisticsEnabled = true,
                isTurnLimitEnabled = true,
                isPaused = false
            )
        }
}