package com.alextos.darts.statistics.presentation.biggest_final_set

import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.statistics.domain.models.StatisticsSet

data class BiggestFinalSetState(
    val biggestFinalSetOfAll: Set? = null,
    val playersBiggestFinalSets: List<StatisticsSet> = listOf()
)
