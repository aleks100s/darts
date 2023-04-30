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
import com.alextos.darts.android.statistics.best_turn.BestTurnScreen
import com.alextos.darts.android.statistics.biggest_final_turn.AndroidBiggestFinalTurnViewModel
import com.alextos.darts.android.statistics.biggest_final_turn.BiggestFinalTurnScreen
import com.alextos.darts.android.statistics.heatmap.AndroidSectorHeatmapViewModel
import com.alextos.darts.android.statistics.heatmap.SectorHeatmapScreen
import com.alextos.darts.android.statistics.player_list.AndroidPlayerListViewModel
import com.alextos.darts.android.statistics.player_list.PlayerListScreen
import com.alextos.darts.android.statistics.shot_distribution.AndroidShotDistributionViewModel
import com.alextos.darts.android.statistics.shot_distribution.ShotDistributionScreen
import com.alextos.darts.android.statistics.statistics.StatisticsScreen
import com.alextos.darts.android.statistics.victory_distribution.AndroidVictoryDistributionViewModel
import com.alextos.darts.android.statistics.victory_distribution.VictoryDistributionScreen
import com.alextos.darts.statistics.presentation.average_values.AverageValuesState
import com.alextos.darts.statistics.presentation.best_set.BestTurnEvent
import com.alextos.darts.statistics.presentation.best_set.BestTurnState
import com.alextos.darts.statistics.presentation.biggest_final_turn.BiggestFinalTurnEvent
import com.alextos.darts.statistics.presentation.biggest_final_turn.BiggestFinalTurnState
import com.alextos.darts.statistics.presentation.heatmap.SectorHeatmapState
import com.alextos.darts.statistics.presentation.player_list.PlayerListEvent
import com.alextos.darts.statistics.presentation.player_list.PlayerListState
import com.alextos.darts.statistics.presentation.shot_distribution.ShotDistributionState
import com.alextos.darts.statistics.presentation.statistics.StatisticsEvent
import com.alextos.darts.statistics.presentation.victory_distribution.VictoryDistributionState
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
            StatisticsScreen { event ->
                when(event) {
                    is StatisticsEvent.ShowBestTurn -> {
                        navController.navigate(route = StatisticsRoute.BestSet.route)
                    }
                    is StatisticsEvent.ShowBiggestFinalTurn -> {
                        navController.navigate(route = StatisticsRoute.BiggestFinalSet.route)
                    }
                    is StatisticsEvent.ShowAverageValues -> {
                        navController.navigate(route = StatisticsRoute.AverageValues.route)
                    }
                    is StatisticsEvent.ShowShotDistribution -> {
                        navController.navigate(route = StatisticsRoute.PlayerList.routeWithArgs("shot"))
                    }
                    is StatisticsEvent.ShowVictoryDistribution -> {
                        navController.navigate(route = StatisticsRoute.PlayerList.routeWithArgs("victory"))
                    }
                    is StatisticsEvent.ShowSectorHeatmapDistribution -> {
                        navController.navigate(route = StatisticsRoute.PlayerList.routeWithArgs("heatmap"))
                    }
                }
            }
        }

        composable(route = StatisticsRoute.BestSet.route) {
            val viewModel: AndroidBestTurnViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState(initial = BestTurnState())
            BestTurnScreen(state = state) { event ->
                when(event) {
                    is BestTurnEvent.ShowBestTurnOfPlayer -> {
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
        }

        composable(route = StatisticsRoute.BiggestFinalSet.route) {
            val viewModel: AndroidBiggestFinalTurnViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState(initial = BiggestFinalTurnState())
            BiggestFinalTurnScreen(state = state) { event ->
                when (event) {
                    is BiggestFinalTurnEvent.ShowBiggestFinalTurnOfPlayer -> {
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
        }

        composable(route = StatisticsRoute.AverageValues.route) {
            val viewModel: AndroidAverageValuesViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState(initial = AverageValuesState())
            AverageValuesScreen(state = state)
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
            val state by viewModel.state.collectAsState(initial = PlayerListState())
            val title = when (mode) {
                "shot" -> stringResource(id = R.string.shot_distribution_statistics)
                "victory" -> stringResource(id = R.string.victory_distribution_statistics)
                "heatmap" -> stringResource(id = R.string.sector_heatmap_statistics)
                else -> ""
            }
            PlayerListScreen(
                title = title,
                state = state
            ) { event ->
                when (event) {
                    is PlayerListEvent.SelectPlayer -> {
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
            val state by viewModel.state.collectAsState(initial = ShotDistributionState())
            ShotDistributionScreen(state = state)
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
            val state by viewModel.state.collectAsState(initial = VictoryDistributionState())
            VictoryDistributionScreen(state = state)
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
            val state by viewModel.state.collectAsState(initial = SectorHeatmapState())
            SectorHeatmapScreen(state = state)
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
            DartsScreen(state = state)
        }
    }
}
