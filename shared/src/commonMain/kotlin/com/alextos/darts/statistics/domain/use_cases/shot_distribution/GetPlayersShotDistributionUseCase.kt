package com.alextos.darts.statistics.domain.use_cases.shot_distribution

import com.alextos.darts.core.domain.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.PlayerShotDistribution
import kotlinx.coroutines.flow.Flow

class GetPlayerShotDistributionUseCase(
    private val statisticsDataSource: StatisticsDataSource
) {
    fun execute(player: Player): Flow<PlayerShotDistribution> {
        return statisticsDataSource.getPlayerShotDistribution(player)
    }
}