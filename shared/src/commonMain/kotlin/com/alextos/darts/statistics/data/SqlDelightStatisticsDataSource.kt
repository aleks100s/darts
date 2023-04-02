package com.alextos.darts.statistics.data

import com.alextos.darts.database.DartsDatabase
import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Set
import com.alextos.darts.core.domain.Shot
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.*
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SqlDelightStatisticsDataSource(
    database: DartsDatabase
): StatisticsDataSource {
    private val queries = database.dartsQueries

    override fun getPlayerBestSet(player: Player): Flow<Set?> {
        return queries.getPlayerBestSet(player.id, player.id)
            .asFlow()
            .mapToList()
            .map { it.playerBestSetToSet() }
    }

    override fun getPlayerBiggestFinalSet(player: Player): Flow<Set?> {
        return queries.getPlayerBiggestFinalSet(player.id)
            .asFlow()
            .mapToList()
            .map { it.playerBiggestFinalSetToSet() }
    }

    override fun getAverageSetScore(): Flow<Double?> {
        return queries.getAverageSetScore()
            .asFlow()
            .map { it.executeAsOne().averageScore }
    }

    override fun getPlayerAverageSetScore(player: Player): Flow<Double?> {
        return queries.getPlayerAverageSetScore(player.id)
            .asFlow()
            .map { it.executeAsOne().averageScore }
    }

    override fun getAverageShotValue(): Flow<Double?> {
        return queries.getAverageShotValue()
            .asFlow()
            .map { it.executeAsOne().averageShotValue }
    }

    override fun getPlayerAverageShotValue(player: Player): Flow<Double?> {
        return queries.getPlayerAverageShotValue(player.id)
            .asFlow()
            .map { it.executeAsOne().averageShotValue }
    }

    override fun getShotDistribution(): Flow<ShotDistribution> {
        return queries.getShotDistribution()
            .asFlow()
            .map { it.executeAsOne() }
            .map { it.toShotDistribution() }
    }

    override fun getPlayerShotDistribution(player: Player): Flow<PlayerShotDistribution> {
        return queries.getPlayerShotDistribution(player.id, player.id, player.id, player.id, player.id, player.id)
            .asFlow()
            .map { it.executeAsOne() }
            .map { it.toPlayerShotDistribution(player) }
    }

    override fun getPlayerVictoryDistribution(player: Player): Flow<PlayerVictoryDistribution> {
        return queries.getVictoryDistribution(player.id, player.id)
            .asFlow()
            .map { it.executeAsOne() }
            .map { it.toPlayerVictoryDistribution(player) }
    }

    override fun getSectorCount(player: Player, sector: Sector): Flow<Pair<Sector, Int>> {
        return queries.getSectorCount(sector.id.toLong(), player.id)
            .asFlow()
            .map { it.executeAsOne() }
            .map { sector to it.toInt() }
    }
}