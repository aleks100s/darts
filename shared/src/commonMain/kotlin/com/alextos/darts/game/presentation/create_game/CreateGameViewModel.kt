package com.alextos.darts.game.presentation.create_game

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.core.domain.GetPlayersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class CreateGameViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(CreateGameState())
    val state = combine(
        _state,
        getPlayersUseCase.execute()
    ) { state, players ->
        if (state.allPlayers != players) {
            state.copy(allPlayers = players)
        } else {
            state
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CreateGameState()
        )
        .toCommonFlow()

    fun onEvent(event: CreateGameEvent) {
        when(event) {
            is CreateGameEvent.SelectPlayer -> {
                _state.update {
                    val list = it.selectedPlayers.toMutableList()
                    if (list.contains(event.player)) {
                        list.remove(event.player)
                    } else {
                        list.add(event.player)
                    }
                    it.copy(selectedPlayers = list)
                }
            }
            is CreateGameEvent.SelectGoal -> {
                _state.update {
                    it.copy(selectedGoal = event.option)
                }
            }
            else -> {}
        }
    }
}