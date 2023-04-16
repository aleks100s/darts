package com.alextos.darts.statistics.presentation.statistics

sealed class StatisticsEvent {
    object ShowBestTurn: StatisticsEvent()
    object ShowBiggestFinalTurn: StatisticsEvent()
    object ShowAverageValues: StatisticsEvent()
    object ShowShotDistribution: StatisticsEvent()
    object ShowVictoryDistribution: StatisticsEvent()
    object ShowSectorHeatmapDistribution: StatisticsEvent()
}