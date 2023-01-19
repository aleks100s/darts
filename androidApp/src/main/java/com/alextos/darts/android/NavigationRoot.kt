package com.alextos.darts.android

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
import com.alextos.darts.android.common.util.toStringNavArgument
import com.alextos.darts.android.game.create_game.AndroidCreateGameViewModel
import com.alextos.darts.android.game.create_game.CreateGameScreen
import com.alextos.darts.android.game.create_player.AndroidCreatePlayerViewModel
import com.alextos.darts.android.game.create_player.CreatePlayerScreen
import com.alextos.darts.android.game.darts.AndroidDartsViewModel
import com.alextos.darts.android.game.darts.DartsScreen
import com.alextos.darts.android.game.game.AndroidGameViewModel
import com.alextos.darts.android.game.game.GameScreen
import com.alextos.darts.android.game.game_list.AndroidGameListViewModel
import com.alextos.darts.android.game.game_list.GameListScreen
import com.alextos.darts.android.game.history.AndroidHistoryViewModel
import com.alextos.darts.android.game.history.HistoryScreen
import com.alextos.darts.game.presentation.create_game.CreateGameEvent
import com.alextos.darts.game.presentation.create_game.CreateGameState
import com.alextos.darts.game.presentation.create_player.CreatePlayerEvent
import com.alextos.darts.game.presentation.create_player.CreatePlayerState
import com.alextos.darts.game.presentation.darts.DartsState
import com.alextos.darts.game.presentation.darts.DartsViewModel
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameState
import com.alextos.darts.game.presentation.game_list.GameListEvent
import com.alextos.darts.game.presentation.game_list.GameListState
import com.alextos.darts.game.presentation.history.HistoryEvent
import com.alextos.darts.game.presentation.history.HistoryState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.GameList.route,
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        composable(route = Route.GameList.route) {
            val viewModel = hiltViewModel<AndroidGameListViewModel>()
            val state by viewModel.state.collectAsState(initial = GameListState())
            GameListScreen(state = state, onEvent = {
                when (it) {
                    is GameListEvent.CreateGame -> {
                        navController.navigate(route = Route.CreateGame.route)
                    }
                    is GameListEvent.SelectGame -> {
                        navController.navigate(
                            route = Route.History.routeWithArgs(
                                it.game.players.toStringNavArgument(),
                                it.game.id.toString()
                            )
                        )
                    }
                    else -> {
                        viewModel.onEvent(it)
                    }
                }
            })
        }

        composable(route = Route.CreateGame.route) {
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
                                    route = Route.Game.routeWithArgs(
                                        state.selectedPlayers.toStringNavArgument(),
                                        state.selectedGoal.toString()
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
            route = Route.Game.route + "/{list}/{goal}",
            arguments = listOf(
                navArgument("list") {
                    type = NavType.StringType
                },
                navArgument("goal") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<AndroidGameViewModel>()
            val state by viewModel.state.collectAsState(initial = GameState())
            GameScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is GameEvent.CloseGame -> {
                            navController.popBackStack()
                            navController.popBackStack()
                        }
                        is GameEvent.ShowDarts -> {
                            navController.navigate(Route.Darts.routeWithArgs(
                                event.turns.map { it.shots }.map { it.map { it.sector }}.toStringNavArgument(),
                                event.turns.indexOf(event.currentSet).toString()
                            ))
                        }
                        else -> { viewModel.onEvent(event) }
                    }
                }
            )
        }

        composable(
            route = Route.History.route + "/{list}/{gameId}",
            arguments = listOf(
                navArgument("list") {
                    type = NavType.StringType
                },
                navArgument("gameId") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<AndroidHistoryViewModel>()
            val state by viewModel.state.collectAsState(initial = HistoryState())
            HistoryScreen(history = state.gameHistory) { event ->
                when (event) {
                    is HistoryEvent.ShowDarts -> {
                        navController.navigate(Route.Darts.routeWithArgs(
                            event.turns.map { it.shots }.map { it.map { it.sector }}.toStringNavArgument(),
                            event.turns.indexOf(event.currentSet).toString()
                        ))
                    }
                }
            }
        }

        composable(
            route = Route.Darts.route + "/{turns}/{currentPage}",
            arguments = listOf(
                navArgument("turns") {
                    type = NavType.StringType
                },
                navArgument("currentPage") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: AndroidDartsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState(initial = DartsState())
            DartsScreen(state = state)
        }
    }
}