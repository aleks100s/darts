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
            return@map Game(
                id = group.firstOrNull()?.gameId,
                players = players,
                winner = null,
                gameGoal = 0,
                timestamp = Instant.fromEpochMilliseconds(0)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
            )
        }
}