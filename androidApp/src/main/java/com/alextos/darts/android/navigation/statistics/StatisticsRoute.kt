package com.alextos.darts.android.navigation.statistics

sealed class StatisticsRoute(val route: String) {
    object Statistics: StatisticsRoute("statistics")
    object BestSet: StatisticsRoute("best_set")
    object MostFrequentShots: StatisticsRoute("most_frequent_shots")
    object BiggestFinalSet: StatisticsRoute("biggest_final_set")
    object AverageValues: StatisticsRoute("average_values")
    object PlayerList: StatisticsRoute("player_list")
    object ShotDistribution: StatisticsRoute("shot_distribution")
    object VictoryDistribution: StatisticsRoute("victory_distribution")
    object SectorHeatmapDistribution: StatisticsRoute("sector_heatmap")
    object Darts: StatisticsRoute("darts")

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { append("/$it") }
        }
    }
}