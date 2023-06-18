package com.alextos.darts.statistics.domain.use_cases.time

import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.TimeDuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class GetGlobalTotalTimePlayedUseCase(
    private val dataSource: StatisticsDataSource,
    private val converter: TimeDurationConverter
) {
    fun execute(): Flow<TimeDuration> {
        return dataSource.getGlobalTotalTimePlayed()
            .mapNotNull { it ?: 0.0 }
            .map { milliseconds ->
                converter.convert(milliseconds)
            }
    }
}