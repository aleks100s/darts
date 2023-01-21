package com.alextos.darts.statistics.presentation.best_set

import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.statistics.domain.models.StatisticsSet

data class BestSetState(
    val bestSetOfAll: Set? = null,
    val playersBestSets: List<StatisticsSet> = listOf()
) {
}