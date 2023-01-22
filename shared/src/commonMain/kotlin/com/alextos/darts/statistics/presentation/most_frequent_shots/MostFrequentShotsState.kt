package com.alextos.darts.statistics.presentation.most_frequent_shots

import com.alextos.darts.game.domain.models.Shot
import com.alextos.darts.statistics.domain.models.StatisticsShots

data class MostFrequentShotsState(
    val mostFrequentShotsOfAll: List<Shot> = listOf(),
    val mostFrequentShots: List<StatisticsShots> = listOf()
)