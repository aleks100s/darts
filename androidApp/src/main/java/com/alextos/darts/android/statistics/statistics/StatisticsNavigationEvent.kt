package com.alextos.darts.android.statistics.statistics

sealed class StatisticsNavigationEvent {
    object ShowBestTurn: StatisticsNavigationEvent()
    object ShowBiggestFinalTurn: StatisticsNavigationEvent()
    object ShowAverageValues: StatisticsNavigationEvent()
    object ShowShotDistribution: StatisticsNavigationEvent()
    object ShowVictoryDistribution: StatisticsNavigationEvent()
    object ShowSectorHeatmapDistribution: StatisticsNavigationEvent()
    object ShowTimeStatistics: StatisticsNavigationEvent()
}