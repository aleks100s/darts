package com.alextos.darts.statistics.domain.use_cases.average_values

import com.alextos.darts.statistics.domain.StatisticsDataSource

class GetAverageShotValueUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(): Double {
        return dataSource.getAverageShotValue() ?: 0.0
    }
}