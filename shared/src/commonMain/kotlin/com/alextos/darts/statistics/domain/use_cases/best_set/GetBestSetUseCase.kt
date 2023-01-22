package com.alextos.darts.statistics.domain.use_cases.best_set

import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.statistics.domain.StatisticsDataSource

class GetBestSetUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(): Set? {
        return dataSource.getBestSet()
    }
}