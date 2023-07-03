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
import com.alextos.darts.android.game.calculator.AndroidCalculatorViewModel
import com.alextos.darts.android.game.calculator.CalculatorNavigationEvent
import com.alextos.darts.android.game.calculator.CalculatorScreen
import com.alextos.darts.android.game.calculator_history.CalculatorHistoryScreen
import com.alextos.darts.android.game.create_game.AndroidCreateGameViewModel
import com.alextos.darts.android.game.create_game.CreateGameNavigationEvent
import com.alextos.darts.android.game.create_game.CreateGameScreen
import com.alextos.darts.android.game.create_game.create_player.AndroidCreatePlayerViewModel
import com.alextos.darts.android.game.create_game.create_player.CreatePlayerScreen
import com.alextos.darts.android.game.darts_board.DartsScreen
import com.alextos.darts.android.game.darts_board.DartsState
import com.alextos.darts.android.game.game.AndroidGameViewModel
import com.alextos.darts.android.game.game.GameNavigationEvent
import com.alextos.darts.android.game.game.GameScreen
import com.alextos.darts.android.game.game.game_settings.GameSettingsScreen
import com.alextos.darts.android.game.game_list.AndroidGameListViewModel
import com.alextos.darts.android.game.game_list.GameListNavigationEvent
import com.alextos.darts.android.game.game_list.GameListScreen
import com.alextos.darts.android.game.history.AndroidHistoryViewModel
import com.alextos.darts.android.game.history.HistoryNavigationEvent
import com.alextos.darts.android.game.history.HistoryScreen
import com.alextos.darts.android.game.in_game_history.InGameHistoryNavigationEvent
import com.alextos.darts.android.game.in_game_history.InGameHistoryScreen
import com.alextos.darts.android.game.recap.RecapScreen
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.game.domain.models.GameSettings
import com.alextos.darts.game.presentation.game.GameState
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
            val state by viewModel.state.collectAsState()

            GameListScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigation = {
                    when (it) {
                        is GameListNavigationEvent.CreateGame -> {
                            navController.navigate(route = GameRoute.CreateGame.route)
                        }
                        is GameListNavigationEvent.SelectGame -> {
                            navController.navigate(
                                route = GameRoute.History.routeWithArgs(
                                    Json.encodeToString(it.game)
                                )
                            )
                        }
                        is GameListNavigationEvent.ReplayGame -> {
                            navController.navigate(
                                route = GameRoute.Game.routeWithArgs(
                                    Json.encodeToString(it.game.getGameSettings())
                                )
                            )
                        }
                        is GameListNavigationEvent.ShowCalculator -> {
                            navController.navigate(route = GameRoute.Calculator.route)
                        }
                        is GameListNavigationEvent.BackButtonPressed -> {
                            navController.popBackStack()
                        }
                        is GameListNavigationEvent.ResumeGame -> {
                            // TODO: continue the game
                        }
                    }
                },
                populateDB = {
                    viewModel.prepopulateDatabase()
                }
            )
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
                    val state by viewModel.state.collectAsState()
                    CreatePlayerScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        onNavigateBack = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        }
                    )
                }
            ) {
                val viewModel = hiltViewModel<AndroidCreateGameViewModel>()
                val state by viewModel.state.collectAsState()
                CreateGameScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigation = { event ->
                        when (event) {
                            is CreateGameNavigationEvent.CreateGame -> {
                                navController.navigate(
                                    route = GameRoute.Game.routeWithArgs(
                                        Json.encodeToString(state.getSettings())
                                    )
                                )
                            }
                            is CreateGameNavigationEvent.CreatePlayer -> {
                                coroutineScope.launch {
                                    if (modalSheetState.isVisible)
                                        modalSheetState.hide()
                                    else
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
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
            val state by viewModel.state.collectAsState()

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
                    GameSettingsScreen(gameSettings = settings) {
                        coroutineScope.launch {
                            modalSheetState.hide()
                        }
                    }
                }
            ) {
                GameScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigation = { event ->
                        when (event) {
                            is GameNavigationEvent.CloseGame -> {
                                navController.popBackStack(
                                    GameRoute.GameList.route,
                                    inclusive = false
                                )
                            }

                            is GameNavigationEvent.ShowDarts -> {
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

                            is GameNavigationEvent.ReplayGame -> {
                                navController.popBackStack(
                                    GameRoute.GameList.route,
                                    inclusive = false
                                )
                                navController.navigate(
                                    route = GameRoute.Game.routeWithArgs(
                                        Json.encodeToString(settings)
                                    )
                                )
                            }

                            is GameNavigationEvent.ShowHistory -> {
                                navController.navigate(
                                    route = GameRoute.InGameHistory.routeWithArgs(
                                        Json.encodeToString(state),
                                        event.page.toString()
                                    )
                                )
                            }

                            is GameNavigationEvent.ShowGameSettings -> {
                                coroutineScope.launch {
                                    if (modalSheetState.isVisible)
                                        modalSheetState.hide()
                                    else
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }
                        }
                    }
                )
            }
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
                onNavigation = {
                    when (it) {
                        is InGameHistoryNavigationEvent.SelectTurn -> {
                            navController.navigate(
                                GameRoute.Darts.routeWithArgs(
                                    Json.encodeToString(
                                        DartsState(
                                            turns = it.list,
                                            currentPage = it.index
                                        )
                                    )
                                )
                            )
                        }
                        is InGameHistoryNavigationEvent.BackButtonPressed -> {
                            navController.popBackStack()
                        }
                    }
                }
            )
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
            val state by viewModel.state.collectAsState()
            HistoryScreen(
                state = state,
                onNavigation = { event ->
                    when (event) {
                        is HistoryNavigationEvent.ShowDarts -> {
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

                        is HistoryNavigationEvent.ShowRecap -> {
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

        composable(route = GameRoute.Calculator.route) {
            val viewModel = hiltViewModel<AndroidCalculatorViewModel>()
            val state by viewModel.state.collectAsState()
            CalculatorScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigation = { event ->
                    when (event) {
                        is CalculatorNavigationEvent.BackButtonPressed -> {
                            navController.popBackStack()
                        }
                        is CalculatorNavigationEvent.ShowHistory -> {
                            if (state.turnNumber > 0) {
                                navController.navigate(
                                    route = GameRoute.CalculatorHistory.routeWithArgs(
                                        Json.encodeToString(event.turns)
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }

        composable(
            route = GameRoute.CalculatorHistory.route + "/{turns}",
            arguments = listOf(
                navArgument("turns") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val turns: List<Turn> = navBackStackEntry.arguments?.getString("turns")
                ?.let { Json.decodeFromString(it) } ?: run { return@composable }
            CalculatorHistoryScreen(
                turns = turns,
                onSelect = { turn ->
                    if (turn.shots.isNotEmpty()) {
                        navController.navigate(
                            GameRoute.Darts.routeWithArgs(
                                Json.encodeToString(
                                    DartsState(
                                        turns = turns,
                                        currentPage = turns.indexOf(turn)
                                    )
                                )
                            )
                        )
                    }
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}