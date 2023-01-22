package com.alextos.darts.statistics.domain.use_cases.shot_distribution

import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.ShotDistribution

class GetShotDistributionUseCase(
    private val statisticsDataSource: StatisticsDataSource
) {
    fun execute(): ShotDistribution {
        return statisticsDataSource.getShotDistribution()
    }
}