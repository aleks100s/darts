package com.alextos.darts.android.navigation.statistics

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alextos.darts.android.R
import com.alextos.darts.android.game.darts_board.DartsScreen
import com.alextos.darts.android.game.darts_board.DartsState
import com.alextos.darts.android.navigation.game.GameRoute
import com.alextos.darts.android.statistics.average_values.AndroidAverageValuesViewModel
import com.alextos.darts.android.statistics.average_values.AverageValuesScreen
import com.alextos.darts.android.statistics.best_turn.AndroidBestTurnViewModel
import com.alextos.darts.android.statistics.best_turn.BestTurnNavigationEvent
import com.alextos.darts.android.statistics.best_turn.BestTurnScreen
import com.alextos.darts.android.statistics.biggest_final_turn.AndroidBiggestFinalTurnViewModel
import com.alextos.darts.android.statistics.biggest_final_turn.BiggestFinalTurnNavigationEvent
import com.alextos.darts.android.statistics.biggest_final_turn.BiggestFinalTurnScreen
import com.alextos.darts.android.statistics.heatmap.AndroidSectorHeatmapViewModel
import com.alextos.darts.android.statistics.heatmap.SectorHeatmapScreen
import com.alextos.darts.android.statistics.player_list.AndroidPlayerListViewModel
import com.alextos.darts.android.statistics.player_list.PlayerListNavigationEvent
import com.alextos.darts.android.statistics.player_list.PlayerListScreen
import com.alextos.darts.android.statistics.shot_distribution.AndroidShotDistributionViewModel
import com.alextos.darts.android.statistics.shot_distribution.ShotDistributionScreen
import com.alextos.darts.android.statistics.statistics.StatisticsNavigationEvent
import com.alextos.darts.android.statistics.statistics.StatisticsScreen
import com.alextos.darts.android.statistics.time.AndroidTimeViewModel
import com.alextos.darts.android.statistics.time.TimeScreen
import com.alextos.darts.android.statistics.victory_distribution.AndroidVictoryDistributionViewModel
import com.alextos.darts.android.statistics.victory_distribution.VictoryDistributionScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun StatisticsNavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = StatisticsRoute.Statistics.route,
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        composable(route = StatisticsRoute.Statistics.route) {
            StatisticsScreen(onNavigationEvent = { event ->
                when(event) {
                    is StatisticsNavigationEvent.ShowBestTurn -> {
                        navController.navigate(route = StatisticsRoute.BestSet.route)
                    }
                    is StatisticsNavigationEvent.ShowBiggestFinalTurn -> {
                        navController.navigate(route = StatisticsRoute.BiggestFinalSet.route)
                    }
                    is StatisticsNavigationEvent.ShowAverageValues -> {
                        navController.navigate(route = StatisticsRoute.AverageValues.route)
                    }
                    is StatisticsNavigationEvent.ShowShotDistribution -> {
                        navController.navigate(route = StatisticsRoute.PlayerList.routeWithArgs("shot"))
                    }
                    is StatisticsNavigationEvent.ShowVictoryDistribution -> {
                        navController.navigate(route = StatisticsRoute.PlayerList.routeWithArgs("victory"))
                    }
                    is StatisticsNavigationEvent.ShowSectorHeatmapDistribution -> {
                        navController.navigate(route = StatisticsRoute.PlayerList.routeWithArgs("heatmap"))
                    }
                    is StatisticsNavigationEvent.ShowTimeStatistics -> {
                        navController.navigate(route = StatisticsRoute.Time.route)
                    }
                }
            }) {
                navController.popBackStack()
            }
        }

        composable(route = StatisticsRoute.BestSet.route) {
            val viewModel: AndroidBestTurnViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            BestTurnScreen(
                state = state,
                onNavigationEvent = { event ->
                    when(event) {
                        is BestTurnNavigationEvent.ShowBestTurnOfPlayer -> {
                            navController.navigate(
                                route = StatisticsRoute.Darts.routeWithArgs(
                                    Json.encodeToString(
                                        DartsState(
                                            turns = listOf(event.turn),
                                            currentPage = 0
                                        )
                                    )
                                )
                            )
                        }
                    }
                }
            ) {
                navController.popBackStack()
            }
        }

        composable(route = StatisticsRoute.BiggestFinalSet.route) {
            val viewModel: AndroidBiggestFinalTurnViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            BiggestFinalTurnScreen(
                state = state,
                onNavigationEvent = { event ->
                    when (event) {
                        is BiggestFinalTurnNavigationEvent.ShowBiggestFinalTurnNavigationOfPlayer -> {
                            navController.navigate(
                                route = StatisticsRoute.Darts.routeWithArgs(
                                    Json.encodeToString(
                                        DartsState(
                                            turns = listOf(event.turn),
                                            currentPage = 0
                                        )
                                    )
                                )
                            )
                        }
                    }
                }
            ) {
                navController.popBackStack()
            }
        }

        composable(route = StatisticsRoute.AverageValues.route) {
            val viewModel: AndroidAverageValuesViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            AverageValuesScreen(state = state) {
                navController.popBackStack()
            }
        }

        composable(
            route = StatisticsRoute.PlayerList.route + "/{mode}",
            arguments = listOf(
                navArgument("mode") {
                    type = NavType.StringType
                }
            )
        ) {
            val mode = it.arguments?.getString("mode")
            val viewModel: AndroidPlayerListViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            val title = when (mode) {
                "shot" -> stringResource(id = R.string.shot_distribution_statistics)
                "victory" -> stringResource(id = R.string.victory_distribution_statistics)
                "heatmap" -> stringResource(id = R.string.sector_heatmap_statistics)
                else -> ""
            }
            PlayerListScreen(
                title = title,
                state = state,
                onNavigationEvent = { event ->
                    when (event) {
                        is PlayerListNavigationEvent.SelectNavigationPlayer -> {
                            when (mode) {
                                "shot" -> {
                                    navController.navigate(
                                        StatisticsRoute.ShotDistribution.routeWithArgs(
                                            Json.encodeToString(event.player)
                                        )
                                    )
                                }
                                "victory" -> {
                                    navController.navigate(
                                        StatisticsRoute.VictoryDistribution.routeWithArgs(
                                            Json.encodeToString(event.player)
                                        )
                                    )
                                }
                                "heatmap" -> {
                                    navController.navigate(
                                        StatisticsRoute.SectorHeatmapDistribution.routeWithArgs(
                                            Json.encodeToString(event.player)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            ) {
                navController.popBackStack()
            }
        }

        composable(
            route = StatisticsRoute.ShotDistribution.route + "/{player}",
            arguments = listOf(
                navArgument("player") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: AndroidShotDistributionViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            ShotDistributionScreen(state = state) {
                navController.popBackStack()
            }
        }

        composable(
            route = StatisticsRoute.VictoryDistribution.route + "/{player}",
            arguments = listOf(
                navArgument("player") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: AndroidVictoryDistributionViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            VictoryDistributionScreen(state = state) {
                navController.popBackStack()
            }
        }

        composable(
            route = StatisticsRoute.SectorHeatmapDistribution.route + "/{player}",
            arguments = listOf(
                navArgument("player") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: AndroidSectorHeatmapViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            SectorHeatmapScreen(state = state) {
                navController.popBackStack()
            }
        }

        composable(route = StatisticsRoute.Time.route) {
            val viewModel: AndroidTimeViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            TimeScreen(state = state) {
                navController.popBackStack()
            }
        }

        composable(
            route = GameRoute.Darts.route + "/{state}",
            arguments = listOf(
                navArgument("state") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val state: DartsState = navBackStackEntry.arguments?.getString("state")
                ?.let { Json.decodeFromString(it) } ?: run { return@composable }
            DartsScreen(state = state) {
                navController.popBackStack()
            }
        }
    }
}
