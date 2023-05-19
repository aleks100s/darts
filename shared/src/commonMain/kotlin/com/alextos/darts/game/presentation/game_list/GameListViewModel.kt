package com.alextos.darts.game.presentation.game_list

import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.game.domain.useCases.DeleteGameUseCase
import com.alextos.darts.game.domain.useCases.GetGamesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameListViewModel(
    private val deleteGameUseCase: DeleteGameUseCase,
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
            state.copy(games = games, isLoading = false)
        } else {
            state
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GameListState()
        )
        .toCommonStateFlow()

    fun onEvent(event: GameListEvent) {
        when (event) {
            is GameListEvent.CreateGame -> {}
            is GameListEvent.SelectGame -> {}
            is GameListEvent.DeleteGame -> {
                _state.value.gameToDelete?.let {
                    viewModelScope.launch(Dispatchers.Default) {
                        deleteGameUseCase.execute(it)
                    }
                    _state.update {
                        it.copy(isDeleteGameDialogShown = false)
                    }
                }
            }
            is GameListEvent.ShowDeleteGameDialog -> {
                _state.update {
                    it.copy(
                        isDeleteGameDialogShown = true,
                        gameToDelete = event.game
                    )
                }
            }
            is GameListEvent.HideDeleteGameDialog -> {
                _state.update { it.copy(isDeleteGameDialogShown = false) }
            }
        }
    }
}