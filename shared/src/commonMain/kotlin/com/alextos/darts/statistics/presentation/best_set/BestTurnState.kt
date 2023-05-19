package com.alextos.darts.statistics.presentation.best_set

import com.alextos.darts.statistics.domain.models.StatisticsSet

data class BestTurnState(
    val playersBestTurns: List<StatisticsSet> = listOf(),
    val isLoading: Boolean = true
)