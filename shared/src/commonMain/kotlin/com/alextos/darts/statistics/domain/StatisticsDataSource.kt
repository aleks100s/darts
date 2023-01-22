package com.alextos.darts.statistics.domain

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Set
import com.alextos.darts.core.domain.Shot
import com.alextos.darts.statistics.domain.models.PlayerShotDistribution
import com.alextos.darts.statistics.domain.models.ShotDistribution

interface StatisticsDataSource {
    fun getBestSet(): Set?
    fun getPlayerBestSet(player: Player): Set?
    fun getMostFrequentShots(): List<Shot>
    fun getPlayerMostFrequentShots(player: Player): List<Shot>
    fun getBiggestFinalSet(): Set?
    fun getPlayerBiggestFinalSet(player: Player): Set?
    fun getAverageSetScore(): Double?
    fun getPlayerAverageSetScore(player: Player): Double?
    fun getAverageShotValue(): Double?
    fun getPlayerAverageShotValue(player: Player): Double?
    fun getShotDistribution(): ShotDistribution
    fun getPlayerShotDistribution(player: Player): PlayerShotDistribution
}