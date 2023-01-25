package com.alextos.darts.statistics.domain.use_cases.most_frequent_shots

import com.alextos.darts.core.domain.Shot
import com.alextos.darts.statistics.domain.StatisticsDataSource

class GetMostFrequentShotsUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(): List<Shot> {
        return dataSource.getMostFrequentShots()
    }
}