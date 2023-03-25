package com.alextos.darts.statistics.domain

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Set
import com.alextos.darts.core.domain.Shot
import com.alextos.darts.statistics.domain.models.PlayerShotDistribution
import com.alextos.darts.statistics.domain.models.PlayerVictoryDistribution
import com.alextos.darts.statistics.domain.models.SectorHeatmapDistribution
import com.alextos.darts.statistics.domain.models.ShotDistribution

interface StatisticsDataSource {
    fun getPlayerBestSet(player: Player): Set?
    fun getPlayerMostFrequentShots(player: Player): List<Shot>
    fun getPlayerBiggestFinalSet(player: Player): Set?
    fun getAverageSetScore(): Double?
    fun getPlayerAverageSetScore(player: Player): Double?
    fun getAverageShotValue(): Double?
    fun getPlayerAverageShotValue(player: Player): Double?
    fun getShotDistribution(): ShotDistribution
    fun getPlayerShotDistribution(player: Player): PlayerShotDistribution
    fun getPlayerVictoryDistribution(player: Player): PlayerVictoryDistribution
    fun getSectorCount(player: Player, sector: Sector): Int
}