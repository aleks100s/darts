package com.alextos.darts.android.navigation.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alextos.darts.android.common.util.toPlayerList
import com.alextos.darts.android.common.util.toStringNavArgument
import com.alextos.darts.android.game.create_game.AndroidCreateGameViewModel
import com.alextos.darts.android.game.create_game.CreateGameScreen
import com.alextos.darts.android.game.create_player.AndroidCreatePlayerViewModel
import com.alextos.darts.android.game.create_player.CreatePlayerScreen
import com.alextos.darts.android.game.darts_board.DartsScreen
import com.alextos.darts.android.common.util.toShots
import com.alextos.darts.android.game.game.AndroidGameViewModel
import com.alextos.darts.android.game.game.GameScreen
import com.alextos.darts.android.game.game_list.AndroidGameListViewModel
import com.alextos.darts.android.game.game_list.GameListScreen
import com.alextos.darts.android.game.history.AndroidHistoryViewModel
import com.alextos.darts.android.game.history.HistoryScreen
import com.alextos.darts.android.game.in_game_history.InGameHistory
import com.alextos.darts.android.game.recap.RecapScreen
import com.alextos.darts.game.presentation.create_game.CreateGameEvent
import com.alextos.darts.game.presentation.create_game.CreateGameState
import com.alextos.darts.game.presentation.create_player.CreatePlayerEvent
import com.alextos.darts.game.presentation.create_player.CreatePlayerState
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameState
import com.alextos.darts.game.presentation.game_list.GameListEvent
import com.alextos.darts.game.presentation.game_list.GameListState
import com.alextos.darts.game.presentation.history.HistoryEvent
import com.alextos.darts.game.presentation.history.HistoryState
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameNavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = GameRoute.GameList.route,
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        composable(route = GameRoute.GameList.route) {
            val viewModel = hiltViewModel<AndroidGameListViewModel>()
            val state by viewModel.state.collectAsState(initial = GameListState())

            GameListScreen(state = state, onEvent = {
                when (it) {
                    is GameListEvent.CreateGame -> {
                        navController.navigate(route = GameRoute.CreateGame.route)
                    }
                    is GameListEvent.SelectGame -> {
                        navController.navigate(
                            route = GameRoute.History.routeWithArgs(
                                it.game.players.toStringNavArgument(),
                                it.game.id.toString(),
                                it.game.gameGoal.toString()
                            )
                        )
                    }
                    else -> {
                        viewModel.onEvent(it)
                    }
                }
            })
        }

        composable(route = GameRoute.CreateGame.route) {
            val coroutineScope = rememberCoroutineScope()
            val modalSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
                skipHalfExpanded = true
            )

            BackHandler(modalSheetState.isVisible) {
                coroutineScope.launch { modalSheetState.hide() }
            }

            ModalBottomSheetLayout(
                sheetState = modalSheetState,
                sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                sheetContent = {
                    val viewModel = hiltViewModel<AndroidCreatePlayerViewModel>()
                    val state by viewModel.state.collectAsState(initial = CreatePlayerState())
                    CreatePlayerScreen(
                        state = state,
                        onEvent = {
                            viewModel.onEvent(it)
                            when (it) {
                                is CreatePlayerEvent.SavePlayer -> {
                                    coroutineScope.launch {
                                        modalSheetState.hide()
                                    }
                                }
                                else -> { }
                            }
                        }
                    )
                }
            ) {
                val viewModel = hiltViewModel<AndroidCreateGameViewModel>()
                val state by viewModel.state.collectAsState(initial = CreateGameState())
                CreateGameScreen(
                    state = state,
                    onEvent = {
                        when(it) {
                            is CreateGameEvent.CreateGame -> {
                                navController.navigate(
                                    route = GameRoute.Game.routeWithArgs(
                                        state.selectedPlayers.toStringNavArgument(),
                                        state.selectedGoal.toString(),
                                        state.isFinishWithDoublesChecked.toString()
                                    )
                                )
                            }
                            is CreateGameEvent.CreatePlayer -> {
                                coroutineScope.launch {
                                    if (modalSheetState.isVisible)
                                        modalSheetState.hide()
                                    else
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }
                            else -> {
                                viewModel.onEvent(it)
                            }
                        }
                    }
                )
            }
        }

        composable(
            route = GameRoute.Game.route + "/{list}/{goal}/{doubles}",
            arguments = listOf(
                navArgument("list") {
                    type = NavType.StringType
                },
                navArgument("goal") {
                    type = NavType.StringType
                },
                navArgument("doubles") {
                    type = NavType.BoolType
                }
            )
        ) { navBackStackEntry ->
            val players = navBackStackEntry.arguments?.getString("list")?.toPlayerList() ?: listOf()
            val goal = navBackStackEntry.arguments?.getString("goal")?.toInt() ?: 0
            val finishWithDoubles = navBackStackEntry.arguments?.getBoolean("doubles") ?: false

            val viewModel = hiltViewModel<AndroidGameViewModel>()
            val state by viewModel.state.collectAsState(initial = GameState())
            GameScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is GameEvent.CloseGame -> {
                            navController.popBackStack(GameRoute.GameList.route, inclusive = false)
                        }
                        is GameEvent.ShowDarts -> {
                            navController.navigate(
                                GameRoute.Darts.routeWithArgs(
                                event.turns.map { it.shots }.map { it.map { it.sector }}.toStringNavArgument(),
                                event.turns.indexOf(event.currentSet).toString()
                            ))
                        }
                        is GameEvent.ReplayGame -> {
                            navController.popBackStack(GameRoute.GameList.route, inclusive = false)
                            navController.navigate(
                                route = GameRoute.Game.routeWithArgs(
                                    players.toStringNavArgument(),
                                    goal.toString(),
                                    finishWithDoubles.toString()
                                )
                            )
                        }
                        is GameEvent.ShowHistory -> {
                            navController.navigate(
                                route = GameRoute.InGameHistory.routeWithArgs(
                                    Json.encodeToString(state)
                                )
                            )
                        }
                        else -> { viewModel.onEvent(event) }
                    }
                }
            )
        }

        composable(
            route = GameRoute.InGameHistory.route + "/{gameState}",
            arguments = listOf(
                navArgument("gameState") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val state = backStackEntry.arguments?.getString("gameState")
                ?.let { Json.decodeFromString<GameState>(it) }
            state?.let {
                InGameHistory(gameState = it) { turns, currentTurn ->
                    navController.navigate(
                        GameRoute.Darts.routeWithArgs(
                            turns.map { it.shots }.map { it.map { it.sector }}.toStringNavArgument(),
                            turns.indexOf(currentTurn).toString()
                        ))
                }
            }
        }

        composable(
            route = GameRoute.History.route + "/{list}/{gameId}/{gameGoal}",
            arguments = listOf(
                navArgument("list") {
                    type = NavType.StringType
                },
                navArgument("gameId") {
                    type = NavType.StringType
                },
                navArgument("gameGoal") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<AndroidHistoryViewModel>()
            val state by viewModel.state.collectAsState(initial = HistoryState())
            HistoryScreen(state = state) { event ->
                when (event) {
                    is HistoryEvent.ShowDarts -> {
                        navController.navigate(
                            GameRoute.Darts.routeWithArgs(
                                event.turns.map { it.shots }.map { it.map { it.sector }}.toStringNavArgument(),
                                event.turns.indexOf(event.currentSet).toString()
                            )
                        )
                    }

                    is HistoryEvent.ShowRecap -> {
                        navController.navigate(
                            GameRoute.Recap.routeWithArgs(
                                Json.encodeToString(state)
                            )
                        )
                    }
                }
            }
        }

        composable(
            route = GameRoute.Recap.route + "/{historyState}",
            arguments = listOf(
                navArgument("historyState") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val historyState = navBackStackEntry.arguments?.getString("historyState")
                ?.let { Json.decodeFromString<HistoryState>(it) }
            historyState?.let {
                RecapScreen(historyState = it)
            }
        }

        composable(
            route = GameRoute.Darts.route + "/{turns}/{currentPage}",
            arguments = listOf(
                navArgument("turns") {
                    type = NavType.StringType
                },
                navArgument("currentPage") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val turns = navBackStackEntry.arguments?.getString("turns")?.toShots() ?: listOf()
            val currentPage = navBackStackEntry.arguments?.getString("currentPage")?.toInt() ?: 0
            DartsScreen(turns = turns, currentPage = currentPage)
        }
    }
}