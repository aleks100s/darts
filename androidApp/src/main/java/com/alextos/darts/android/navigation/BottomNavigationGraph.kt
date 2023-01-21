package com.alextos.darts.android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alextos.darts.android.navigation.game.GameNavigationRoot
import com.alextos.darts.android.navigation.statistics.StatisticsNavigationRoot

@Composable
fun BottomNavigationGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Game.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(BottomNavItem.Game.route) {
            GameNavigationRoot()
        }
        composable(BottomNavItem.Statistics.route) {
            StatisticsNavigationRoot()
        }
    }
}