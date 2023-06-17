package com.alextos.darts.statistics.domain.use_cases.time

import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.TimeDuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class GetGlobalTotalTimePlayedUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(): Flow<TimeDuration> {
        return dataSource.getGlobalTotalTimePlayed()
            .mapNotNull { it ?: 0.0 }
            .map { milliseconds ->
                val totalSeconds = milliseconds / 1000
                val hours = totalSeconds / 3600
                val hourReminder = (totalSeconds % 3600)
                val minutes = hourReminder / 60
                val seconds = hourReminder % 60
                TimeDuration(
                    hours = hours.toInt(),
                    minutes = minutes.toInt(),
                    seconds = seconds.toInt()
                )
            }
    }
}