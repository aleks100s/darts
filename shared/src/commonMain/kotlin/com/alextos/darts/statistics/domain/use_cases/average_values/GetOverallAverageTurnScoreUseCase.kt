package com.alextos.darts.statistics.domain.use_cases.average_values

import com.alextos.darts.statistics.domain.StatisticsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class GetOverallAverageTurnScoreUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(): Flow<Double> {
        return dataSource.getAverageTurnScore()
            .mapNotNull { it ?: 0.0 }
    }
}