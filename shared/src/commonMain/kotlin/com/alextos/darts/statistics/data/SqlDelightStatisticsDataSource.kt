package com.alextos.darts.statistics.data

import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.game.domain.models.Player
import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.game.domain.models.Shot
import com.alextos.darts.statistics.domain.StatisticsDataSource

class SqlDelightStatisticsDataSource(
    database: DartsDatabase
): StatisticsDataSource {
    private val queries = database.dartsQueries

    override fun getBestSet(): Set? {
        return queries.getBestSet()
            .executeAsList()
            .bestSetToSet()
    }

    override fun getPlayerBestSet(player: Player): Set? {
        return queries.getPlayerBestSet(player.id, player.id)
            .executeAsList()
            .playerBestSetToSet()
    }

    override fun getMostFrequentShots(): List<Shot> {
        return queries.getMostFrequentSectors()
            .executeAsList()
            .sectorsToShots()
    }

    override fun getPlayerMostFrequentShots(player: Player): List<Shot> {
        return queries.getPlayerMostFrequentSectors(player.id)
            .executeAsList()
            .playerSectorsToShots()
    }
}