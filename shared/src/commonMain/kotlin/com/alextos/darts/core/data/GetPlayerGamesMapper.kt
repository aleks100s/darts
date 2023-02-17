package com.alextos.darts.core.data

import com.alextos.darts.core.domain.Player
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
            val entity = group.find { it.isWinner == 1L }!!
            val winner = Player(entity.playerId, entity.playerName)
            return@map Game(
                id = entity.gameId,
                players = players,
                winner = winner,
                gameGoal = entity.goal.toInt(),
                timestamp = Instant.fromEpochMilliseconds(entity.timestamp)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
            )
        }
}