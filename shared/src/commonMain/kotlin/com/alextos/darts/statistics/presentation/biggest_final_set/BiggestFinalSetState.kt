package com.alextos.darts.statistics.presentation.biggest_final_set

import com.alextos.darts.statistics.domain.models.StatisticsSet

data class BiggestFinalSetState(
    val playersBiggestFinalSets: List<StatisticsSet> = listOf()
)
