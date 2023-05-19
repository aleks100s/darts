package com.alextos.darts.statistics.presentation.biggest_final_turn

import com.alextos.darts.statistics.domain.models.StatisticsSet

data class BiggestFinalTurnState(
    val playersBiggestFinalTurns: List<StatisticsSet> = listOf(),
    val isLoading: Boolean = true
)
