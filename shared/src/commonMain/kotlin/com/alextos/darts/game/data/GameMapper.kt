package com.alextos.darts.game.data

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.core.domain.model.Player
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
                finishWithDoubles = gameEntity?.finishWithDoubles == 1L,
                randomPlayerOrder = gameEntity?.randomPlayerOrder == 1L,
                enableStatistics = gameEntity?.disableStatistics != 1L
            )
        }
}