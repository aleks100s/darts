package com.alextos.darts.statistics.presentation.statistics

sealed class StatisticsEvent {
    object ShowBestSet: StatisticsEvent()
    object ShowMostFrequentShots: StatisticsEvent()
}