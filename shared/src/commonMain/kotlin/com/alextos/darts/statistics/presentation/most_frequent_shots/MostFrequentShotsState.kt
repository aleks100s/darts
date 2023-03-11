package com.alextos.darts.statistics.presentation.most_frequent_shots

import com.alextos.darts.core.domain.Shot
import com.alextos.darts.statistics.domain.models.StatisticsShots

data class MostFrequentShotsState(
    val mostFrequentShots: List<StatisticsShots> = listOf()
)