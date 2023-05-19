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
import com.alextos.darts.android.game.create_game.AndroidCreateGameViewModel
import com.alextos.darts.android.game.create_game.CreateGameScreen
import com.alextos.darts.android.game.create_player.AndroidCreatePlayerViewModel
import com.alextos.darts.android.game.create_player.CreatePlayerScreen
import com.alextos.darts.android.game.darts_board.DartsScreen
import com.alextos.darts.android.game.darts_board.DartsState
import com.alextos.darts.android.game.game.AndroidGameViewModel
import com.alextos.darts.android.game.game.GameScreen
import com.alextos.darts.android.game.game_list.AndroidGameListViewModel
import com.alextos.darts.android.game.game_list.GameListScreen
import com.alextos.darts.android.game.history.AndroidHistoryViewModel
import com.alextos.darts.android.game.history.HistoryScreen
import com.alextos.darts.android.game.in_game_history.InGameHistoryScreen
import com.alextos.darts.android.game.recap.RecapScreen
import com.alextos.darts.game.domain.models.GameSettings
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

            GameListScreen(
                state = state,
                onEvent = {
                    when (it) {
                        is GameListEvent.CreateGame -> {
                            navController.navigate(route = GameRoute.CreateGame.route)
                        }
                        is GameListEvent.SelectGame -> {
                            navController.navigate(
                                route = GameRoute.History.routeWithArgs(
                                    Json.encodeToString(it.game)
                                )
                            )
                        }
                        is GameListEvent.ReplayGame -> {
                            viewModel.onEvent(it)
                            navController.navigate(
                                route = GameRoute.Game.routeWithArgs(
                                    Json.encodeToString(it.game.getGameSettings())
                                )
                            )
                        }
                        else -> {
                            viewModel.onEvent(it)
                        }
                    }
                }
            ) {
                navController.popBackStack()
            }
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
                                        Json.encodeToString(state.getSettings())
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
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(
            route = GameRoute.Game.route + "/{settings}",
            arguments = listOf(
                navArgument("settings") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val settings: GameSettings = navBackStackEntry.arguments?.getString("settings")
                ?.let { Json.decodeFromString(it) }
                ?: run { return@composable }
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
                                    Json.encodeToString(
                                        DartsState(
                                            event.turns,
                                            event.currentPage
                                        )
                                    )
                                )
                            )
                        }
                        is GameEvent.ReplayGame -> {
                            navController.popBackStack(GameRoute.GameList.route, inclusive = false)
                            navController.navigate(
                                route = GameRoute.Game.routeWithArgs(
                                    Json.encodeToString(settings)
                                )
                            )
                        }
                        is GameEvent.ShowHistory -> {
                            navController.navigate(
                                route = GameRoute.InGameHistory.routeWithArgs(
                                    Json.encodeToString(state),
                                    event.page.toString()
                                )
                            )
                        }
                        else -> { viewModel.onEvent(event) }
                    }
                }
            )
        }

        composable(
            route = GameRoute.InGameHistory.route + "/{gameState}/{page}",
            arguments = listOf(
                navArgument("gameState") {
                    type = NavType.StringType
                },
                navArgument("page") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val state = backStackEntry.arguments?.getString("gameState")
                ?.let { Json.decodeFromString<GameState>(it) }
                ?: run { return@composable }
            val page = backStackEntry.arguments?.getInt("page") ?: run { return@composable }
            InGameHistoryScreen(
                gameState = state,
                currentPage = page,
                onSelect = { turns, currentPage ->
                    navController.navigate(
                        GameRoute.Darts.routeWithArgs(
                            Json.encodeToString(
                                DartsState(
                                    turns = turns,
                                    currentPage = currentPage
                                )
                            )
                        )
                    )
                }
            ) {
                navController.popBackStack()
            }
        }

        composable(
            route = GameRoute.History.route + "/{game}",
            arguments = listOf(
                navArgument("game") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<AndroidHistoryViewModel>()
            val state by viewModel.state.collectAsState(initial = HistoryState())
            HistoryScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is HistoryEvent.ShowDarts -> {
                            navController.navigate(
                                GameRoute.Darts.routeWithArgs(
                                    Json.encodeToString(
                                        DartsState(
                                            turns = event.turns,
                                            currentPage = event.currentPage
                                        )
                                    )
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
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
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
                ?: run { return@composable }
            RecapScreen(historyState = historyState) {
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