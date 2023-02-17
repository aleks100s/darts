package com.alextos.darts.core.data

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.PlayersDataSource
import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.game.data.mapToGames
import com.alextos.darts.game.data.toPlayer
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SqlDelightPlayersDataSource(
    database: DartsDatabase
): PlayersDataSource {
    private val queries = database.dartsQueries

    override fun getPlayers(): Flow<List<Player>> {
        return queries.getPlayers()
            .asFlow()
            .mapToList()
            .map { players ->
                players.map { it.toPlayer() }
            }
    }

    override fun deletePlayer(player: Player) {
        val games = queries.getPlayerGames(player_id = player.id)
            .executeAsList()
            .mapToGames()
        games.forEach { game ->
            game.id?.let {
                queries.deleteGame(it)
            }
        }
        queries.deletePlayer(player.id)
    }
}