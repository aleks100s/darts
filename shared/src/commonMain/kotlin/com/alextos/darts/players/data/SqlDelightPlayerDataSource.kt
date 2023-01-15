package com.alextos.darts.players.data

import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.players.domain.models.Player
import com.alextos.darts.players.domain.PlayerDataSource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SqlDelightPlayerDataSource(
    database: DartsDatabase
): PlayerDataSource {
    private val queries = database.dartsQueries

    override fun getPlayers(): Flow<List<Player>> {
        return queries.getPlayers()
            .asFlow()
            .mapToList()
            .map { players ->
                players.map { it.toPlayer() }
            }
    }

    override fun createPlayer(name: String) {
        queries.insertPlayerEntity(id = null, name = name)
    }

    private fun getLastInsertedId(): Long {
        return queries.getLastInsertedId().executeAsOne()
    }
}