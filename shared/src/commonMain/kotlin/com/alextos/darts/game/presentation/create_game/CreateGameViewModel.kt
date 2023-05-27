package com.alextos.darts.game.presentation.create_game

import com.alextos.darts.core.domain.useCases.DeletePlayerUseCase
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreateGameViewModel(
    private val deletePlayerUseCase: DeletePlayerUseCase,
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
        .toCommonStateFlow()

    fun onEvent(event: CreateGameEvent) {
        when(event) {
            is CreateGameEvent.SelectPlayer -> {
                _state.update {
                    val player = event.player
                    val list = it.selectedPlayers.toMutableList()
                    if (list.contains(player)) {
                        list.remove(player)
                    } else {
                        list.add(player)
                    }
                    it.copy(selectedPlayers = list)
                }
            }
            is CreateGameEvent.SelectGoal -> {
                _state.update {
                    it.copy(selectedGoal = event.option)
                }
            }
            is CreateGameEvent.ShowDeletePlayerDialog -> {
                _state.update {
                    it.copy(
                        isDeletePlayerDialogShown = true,
                        playerToDelete = event.player
                    )
                }
            }
            is CreateGameEvent.HideDeletePlayerDialog -> {
                _state.update {
                    it.copy(isDeletePlayerDialogShown = false)
                }
            }
            is CreateGameEvent.DeletePlayer -> {
                _state.value.playerToDelete?.let {
                    viewModelScope.launch(Dispatchers.Default) {
                        deletePlayerUseCase.execute(it)
                    }
                }
                _state.update {
                    it.copy(
                        isDeletePlayerDialogShown = false
                    )
                }
            }
            is CreateGameEvent.ToggleFinishWithDoubles -> {
                _state.update {
                    it.copy(isFinishWithDoublesChecked = event.isChecked)
                }
            }
            is CreateGameEvent.ToggleRandomPlayersOrder -> {
                _state.update {
                    it.copy(isRandomPlayersOrderChecked = event.isChecked)
                }
            }

            CreateGameEvent.CreateGame -> {}
            CreateGameEvent.CreatePlayer -> {}
        }
    }
}