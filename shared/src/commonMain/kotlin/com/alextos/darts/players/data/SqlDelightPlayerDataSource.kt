package com.alextos.darts.players.data

import com.alextos.darts.core.util.CommonFlow
import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.players.domain.models.Player
import com.alextos.darts.players.domain.PlayerDataSource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map

class SqlDelightPlayerDataSource(
    database: DartsDatabase
): PlayerDataSource {
    private val queries = database.dartsQueries

    override fun getPlayers(): CommonFlow<List<Player>> {
        return queries.getPlayers()
            .asFlow()
            .mapToList()
            .map { players ->
                players.map { it.toPlayer() }
            }
            .toCommonFlow()
    }

    override fun createPlayer(name: String): Player {
        queries.insertPlayerEntity(id = null, name = name)
        val id = getLastInsertedId()
        return getPlayer(id)
    }

    // TODO: Check if needed (on game creation)
    private fun getPlayer(id: Long): Player {
        return queries.getPlayer(id)
            .executeAsOne()
            .toPlayer()
    }

    private fun getLastInsertedId(): Long {
        return queries.getLastInsertedId().executeAsOne()
    }
}