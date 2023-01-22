package com.alextos.darts.statistics.presentation.biggest_final_set

import com.alextos.darts.core.domain.Set
import com.alextos.darts.statistics.domain.models.StatisticsSet

data class BiggestFinalSetState(
    val biggestFinalSetOfAll: Set? = null,
    val playersBiggestFinalSets: List<StatisticsSet> = listOf()
)
