package com.alextos.darts.statistics.presentation.best_set

import com.alextos.darts.statistics.domain.models.StatisticsSet

data class BestSetState(
    val playersBestSets: List<StatisticsSet> = listOf()
)