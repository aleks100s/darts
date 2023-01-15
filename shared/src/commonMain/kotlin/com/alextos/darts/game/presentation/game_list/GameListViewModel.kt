package com.alextos.darts.game.presentation.game_list

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.game.domain.useCases.GetGamesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class GameListViewModel(
    getGamesUseCase: GetGamesUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(GameListState())
    val state = combine(
        _state,
        getGamesUseCase.execute()
    ) { state, games ->
        if (games != state.games) {
            state.copy(games = games)
        } else {
            state
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GameListState()
        )
        .toCommonFlow()

    fun onEvent(event: GameListEvent) {
        when (event) {
            is GameListEvent.CreateGame -> {
                // TODO: create game
            }
            is GameListEvent.SelectGame -> {
                // TODO: select game
            }
        }
    }
}