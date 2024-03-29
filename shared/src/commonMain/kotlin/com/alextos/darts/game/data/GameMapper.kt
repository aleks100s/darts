package com.alextos.darts.game.data

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.models.PlayerHistory
import database.GetGames
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun List<GetGames>.mapToGames(): List<Game> {
    return this.groupBy { it.gameId }
        .entries
        .map { (_, group)  ->
            val players = group.map { Player(it.playerId, it.playerName) }
            val gameEntity = group.firstOrNull()
            val winnerEntity = group.find { it.isWinner == 1L }
            val winner = if (winnerEntity == null) null else Player(winnerEntity.playerId, winnerEntity.playerName)
            return@map Game(
                id = gameEntity?.gameId ?: 0,
                players = players,
                winner = winner,
                gameGoal = gameEntity?.goal?.toInt() ?: 0,
                finishTimestamp = Instant.fromEpochMilliseconds(gameEntity?.timestamp ?: 0)
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                startTimestamp = Instant.fromEpochMilliseconds(gameEntity?.timestampStart ?: gameEntity?.timestamp ?: 0)
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                isWinWithDoublesEnabled = gameEntity?.finishWithDoubles == 1L,
                isRandomPlayerOrderEnabled = gameEntity?.randomPlayerOrder == 1L,
                isStatisticsEnabled = gameEntity?.disableStatistics != 1L,
                isTurnLimitEnabled = gameEntity?.turnsLimitEnabled == 1L,
                isPaused = gameEntity?.isOngoing == 1L
            )
        }
}