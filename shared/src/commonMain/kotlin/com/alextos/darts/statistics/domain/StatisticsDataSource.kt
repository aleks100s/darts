package com.alextos.darts.statistics.domain

import com.alextos.darts.game.domain.models.Player
import com.alextos.darts.game.domain.models.Set

interface StatisticsDataSource {
    fun getBestSet(): Set?
    fun getPlayerBestSet(player: Player): Set?
}