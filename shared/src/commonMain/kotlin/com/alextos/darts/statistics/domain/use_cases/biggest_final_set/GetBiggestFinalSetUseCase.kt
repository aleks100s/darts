package com.alextos.darts.statistics.domain.use_cases.biggest_final_set

import com.alextos.darts.core.domain.Set
import com.alextos.darts.statistics.domain.StatisticsDataSource

class GetBiggestFinalSetUseCase(
    private val dataSource: StatisticsDataSource
) {

    fun execute(): Set? {
        return dataSource.getBiggestFinalSet()
    }
}