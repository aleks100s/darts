package com.alextos.darts.statistics.presentation.statistics

sealed class StatisticsEvent {
    object ShowBestSet: StatisticsEvent()
    object ShowMostFrequentShots: StatisticsEvent()
    object ShowBiggestFinalSet: StatisticsEvent()
    object ShowAverageValues: StatisticsEvent()
}