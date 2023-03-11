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
import com.alextos.darts.android.common.util.toStringNavArgument
import com.alextos.darts.android.common.presentation.screens.DartsScreen
import com.alextos.darts.android.common.util.toShots
import com.alextos.darts.android.navigation.game.GameRoute
import com.alextos.darts.android.statistics.average_values.AndroidAverageValuesViewModel
import com.alextos.darts.android.statistics.average_values.AverageValuesScreen
import com.alextos.darts.android.statistics.best_set.AndroidBestSetViewModel
import com.alextos.darts.android.statistics.best_set.BestSetScreen
import com.alextos.darts.android.statistics.biggest_final_set.AndroidBiggestFinalSetViewModel
import com.alextos.darts.android.statistics.biggest_final_set.BiggestFinalSetScreen
import com.alextos.darts.android.statistics.most_frequent_shots.AndroidMostFrequentShotsViewModel
import com.alextos.darts.android.statistics.most_frequent_shots.MostFrequentShotsScreen
import com.alextos.darts.android.statistics.player_list.AndroidPlayerListViewModel
import com.alextos.darts.android.statistics.player_list.PlayerListScreen
import com.alextos.darts.android.statistics.shot_distribution.AndroidShotDistributionViewModel
import com.alextos.darts.android.statistics.shot_distribution.ShotDistributionScreen
import com.alextos.darts.android.statistics.statistics.StatisticsScreen
import com.alextos.darts.android.statistics.victory_distribution.AndroidVictoryDistributionViewModel
import com.alextos.darts.android.statistics.victory_distribution.VictoryDistributionScreen
import com.alextos.darts.statistics.presentation.average_values.AverageValuesState
import com.alextos.darts.statistics.presentation.best_set.BestSetEvent
import com.alextos.darts.statistics.presentation.best_set.BestSetState
import com.alextos.darts.statistics.presentation.biggest_final_set.BiggestFinalSetEvent
import com.alextos.darts.statistics.presentation.biggest_final_set.BiggestFinalSetState
import com.alextos.darts.statistics.presentation.most_frequent_shots.MostFrequentShotsEvent
import com.alextos.darts.statistics.presentation.most_frequent_shots.MostFrequentShotsState
import com.alextos.darts.statistics.presentation.player_list.PlayerListEvent
import com.alextos.darts.statistics.presentation.player_list.PlayerListState
import com.alextos.darts.statistics.presentation.shot_distribution.ShotDistributionState
import com.alextos.darts.statistics.presentation.statistics.StatisticsEvent
import com.alextos.darts.statistics.presentation.victory_distribution.VictoryDistributionState

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
                    is StatisticsEvent.ShowBestSet -> {
                        navController.navigate(route = StatisticsRoute.BestSet.route)
                    }
                    is StatisticsEvent.ShowMostFrequentShots -> {
                        navController.navigate(route = StatisticsRoute.MostFrequentShots.route)
                    }
                    is StatisticsEvent.ShowBiggestFinalSet -> {
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
                }
            }
        }

        composable(route = StatisticsRoute.BestSet.route) {
            val viewModel: AndroidBestSetViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState(initial = BestSetState())
            BestSetScreen(state = state) { event ->
                when(event) {
                    is BestSetEvent.ShowBestSetOfPlayer -> {
                        val route = StatisticsRoute.Darts.routeWithArgs(
                            listOf(event.set).map { set ->
                                set.shots.map { it.sector }
                            }.toStringNavArgument()
                        )
                        navController.navigate(
                            route = route
                        )
                    }
                }
            }
        }

        composable(route = StatisticsRoute.MostFrequentShots.route) {
            val viewModel: AndroidMostFrequentShotsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState(initial = MostFrequentShotsState())
            MostFrequentShotsScreen(state = state) { event ->
                when (event) {
                    is MostFrequentShotsEvent.ShowPlayerMostFrequentShots -> {
                        navController.navigate(
                            StatisticsRoute.Darts.routeWithArgs(
                                listOf(event.shots.map { it.sector }).toStringNavArgument()
                            )
                        )
                    }
                }
            }
        }

        composable(route = StatisticsRoute.BiggestFinalSet.route) {
            val viewModel: AndroidBiggestFinalSetViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState(initial = BiggestFinalSetState())
            BiggestFinalSetScreen(state = state) { event ->
                when (event) {
                    is BiggestFinalSetEvent.ShowBiggestFinalSetOfPlayer -> {
                        navController.navigate(
                            route = StatisticsRoute.Darts.routeWithArgs(
                                listOf(event.set).map { set ->
                                    set.shots.map { it.sector }
                                }.toStringNavArgument()
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
                                        listOf(event.player).toStringNavArgument()
                                    )
                                )
                            }
                            "victory" -> {
                                navController.navigate(
                                    StatisticsRoute.VictoryDistribution.routeWithArgs(
                                        listOf(event.player).toStringNavArgument()
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
            route = GameRoute.Darts.route + "/{turns}",
            arguments = listOf(
                navArgument("turns") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val turns = backStackEntry.arguments?.getString("turns")?.toShots() ?: listOf()
            val currentPage = backStackEntry.arguments?.getString("currentPage")?.toInt() ?: 0
            DartsScreen(turns, currentPage)
        }
    }
}
