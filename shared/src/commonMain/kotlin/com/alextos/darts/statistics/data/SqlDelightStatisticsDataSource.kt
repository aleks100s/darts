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

    override fun getBiggestFinalSet(): Set? {
        return queries.getBiggestFinalSet()
            .executeAsList()
            .biggestFinalSetToSet()
    }

    override fun getPlayerBiggestFinalSet(player: Player): Set? {
        return queries.getPlayerBiggestFinalSet(player.id)
            .executeAsList()
            .playerBiggestFinalSetToSet()
    }

    override fun getAverageSetScore(): Double? {
        return queries.getAverageSetScore()
            .executeAsOne()
            .averageScore
    }

    override fun getPlayerAverageSetScore(player: Player): Double? {
        return queries.getPlayerAverageSetScore(player.id)
            .executeAsOne()
            .averageScore
    }

    override fun getAverageShotValue(): Double? {
        return queries.getAverageShotValue()
            .executeAsOne()
            .averageShotValue
    }

    override fun getPlayerAverageShotValue(player: Player): Double? {
        return queries.getPlayerAverageShotValue(player.id)
            .executeAsOne()
            .averageShotValue
    }
}