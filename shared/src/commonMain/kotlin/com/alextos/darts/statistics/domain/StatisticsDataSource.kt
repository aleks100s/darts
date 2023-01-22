package com.alextos.darts.statistics.domain

import com.alextos.darts.game.domain.models.Player
import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.game.domain.models.Shot

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
}