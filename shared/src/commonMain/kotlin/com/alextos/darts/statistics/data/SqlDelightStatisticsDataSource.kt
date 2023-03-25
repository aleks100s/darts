package com.alextos.darts.statistics.data

import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Set
import com.alextos.darts.core.domain.Shot
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.*

class SqlDelightStatisticsDataSource(
    database: DartsDatabase
): StatisticsDataSource {
    private val queries = database.dartsQueries

    override fun getPlayerBestSet(player: Player): Set? {
        return queries.getPlayerBestSet(player.id, player.id)
            .executeAsList()
            .playerBestSetToSet()
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

    override fun getShotDistribution(): ShotDistribution {
        return queries.getShotDistribution()
            .executeAsOne()
            .toShotDistribution()
    }

    override fun getPlayerShotDistribution(player: Player): PlayerShotDistribution {
        return queries.getPlayerShotDistribution(player.id, player.id, player.id, player.id, player.id, player.id)
            .executeAsOne()
            .toPlayerShotDistribution(player)
    }

    override fun getPlayerVictoryDistribution(player: Player): PlayerVictoryDistribution {
        return queries.getVictoryDistribution(player.id, player.id)
            .executeAsOne()
            .toPlayerVictoryDistribution(player)
    }

    override fun getSectorCount(player: Player, sector: Sector): Int {
        return queries.getSectorCount(sector.id.toLong(), player.id)
            .executeAsOne()
            .toInt()
    }
}