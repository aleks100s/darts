package com.alextos.darts.statistics.presentation.most_frequent_shots

import com.alextos.darts.game.domain.models.Shot
import com.alextos.darts.statistics.domain.models.StatisticsShots

sealed class MostFrequentShotsEvent {
    data class ShowMostFrequentShotsOfAll(val shots: List<Shot>): MostFrequentShotsEvent()
    data class ShowPlayerMostFrequentShots(val shots: List<Shot>): MostFrequentShotsEvent()
}