package com.alextos.darts.statistics.presentation.statistics

sealed class StatisticsEvent {
    object ShowBestSet: StatisticsEvent()
    object ShowBiggestFinalSet: StatisticsEvent()
    object ShowAverageValues: StatisticsEvent()
    object ShowShotDistribution: StatisticsEvent()
    object ShowVictoryDistribution: StatisticsEvent()
    object ShowSectorHeatmapDistribution: StatisticsEvent()
}