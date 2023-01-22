package com.alextos.darts.statistics.domain.use_cases.average_values

import com.alextos.darts.statistics.domain.StatisticsDataSource

class GetAverageSetScoreUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(): Double {
        return dataSource.getAverageSetScore() ?: 0.0
    }
}