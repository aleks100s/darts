package com.alextos.darts.game.presentation.game_list

import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.debug.PrepopulateDatabaseUseCase
import com.alextos.darts.game.domain.useCases.DeleteGameUseCase
import com.alextos.darts.game.domain.useCases.GetGamesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameListViewModel(
    private val deleteGameUseCase: DeleteGameUseCase,
    private val prepopulateDatabaseUseCase: PrepopulateDatabaseUseCase,
    getGamesUseCase: GetGamesUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(GameListState())
    val state = combine(
        _state,
        getGamesUseCase.execute()
    ) { state, games ->
        val finishedGames = games.filter { !it.isOngoing }
        val ongoingGames = games.filter { it.isOngoing }
        state.copy(
            isLoading = false,
            finishedGames = finishedGames,
            ongoingGames = ongoingGames
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GameListState()
        )
        .toCommonStateFlow()

    fun onEvent(event: GameListEvent) {
        when (event) {
            is GameListEvent.DeleteGame -> {
                _state.value.selectedGame?.let {
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
                        isActionsDialogShown = false,
                        selectedGame = event.game
                    )
                }
            }
            is GameListEvent.HideDeleteGameDialog -> {
                _state.update { it.copy(isDeleteGameDialogShown = false) }
            }
            is GameListEvent.ShowActionsDialog -> {
                _state.update {
                    it.copy(
                        isActionsDialogShown = true,
                        selectedGame = event.game
                    )
                }
            }
            is GameListEvent.HideActionsDialog -> {
                _state.update { it.copy(isActionsDialogShown = false) }
            }
            is GameListEvent.ReplayGame -> {
                _state.update { it.copy(isActionsDialogShown = false) }
            }
        }
    }

    fun prepopulateDatabase() {
        viewModelScope.launch(Dispatchers.Default) {
            prepopulateDatabaseUseCase.execute()
        }
    }
}