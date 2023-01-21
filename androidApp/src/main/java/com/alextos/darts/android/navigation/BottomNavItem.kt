package com.alextos.darts.android.navigation

import com.alextos.darts.android.R

sealed class BottomNavItem(val route: String, val title: Int, val icon: Int) {
    object Game: BottomNavItem("game", R.string.game, R.drawable.ic_baseline_radar_24)
    object Statistics: BottomNavItem("statistics", R.string.statistics, R.drawable.ic_baseline_query_stats_24)
}
