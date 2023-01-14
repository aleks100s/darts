package com.alextos.darts.game.data

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.players.domain.models.Player
import database.GetGames
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun List<GetGames>.mapToGames(): List<Game> {
    return this.groupBy { it.gameId }
        .entries
        .map { (_, group)  ->
            val players = group.map { Player(it.playerId, it.playerName) }
            val entity = group.find { it.isWinner == 1L }!!
            val winner = Player(entity.playerId, entity.playerName)
            return@map Game(
                players = players,
                winner = winner,
                gameGoal = entity.goal.toInt(),
                timestamp = Instant.fromEpochMilliseconds(entity.timestamp)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
            )
        }
}