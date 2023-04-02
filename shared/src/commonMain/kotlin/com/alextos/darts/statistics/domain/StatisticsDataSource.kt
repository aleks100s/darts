package com.alextos.darts.statistics.domain

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Set
import com.alextos.darts.core.domain.Shot
import com.alextos.darts.statistics.domain.models.PlayerShotDistribution
import com.alextos.darts.statistics.domain.models.PlayerVictoryDistribution
import com.alextos.darts.statistics.domain.models.SectorHeatmapDistribution
import com.alextos.darts.statistics.domain.models.ShotDistribution
import kotlinx.coroutines.flow.Flow

interface StatisticsDataSource {
    fun getPlayerBestSet(player: Player): Flow<Set?>
    fun getPlayerBiggestFinalSet(player: Player): Flow<Set?>
    fun getAverageSetScore(): Flow<Double?>
    fun getPlayerAverageSetScore(player: Player): Flow<Double?>
    fun getAverageShotValue(): Flow<Double?>
    fun getPlayerAverageShotValue(player: Player): Flow<Double?>
    fun getShotDistribution(): Flow<ShotDistribution>
    fun getPlayerShotDistribution(player: Player): Flow<PlayerShotDistribution>
    fun getPlayerVictoryDistribution(player: Player): Flow<PlayerVictoryDistribution>
    fun getSectorCount(player: Player, sector: Sector): Flow<Pair<Sector, Int>>
}