package com.alextos.darts.game.presentation.create_player

import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.game.domain.useCases.CreatePlayerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreatePlayerViewModel(
    private val createPlayerUseCase: CreatePlayerUseCase,
    getPlayersUseCase: GetPlayersUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(CreatePlayerState())
    val state = combine(
        _state,
        getPlayersUseCase.execute()
    ) { state, players ->
        if (state.allPlayers != players) {
            state.copy(allPlayers = players)
        } else {
            state
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CreatePlayerState()
    )
        .toCommonStateFlow()

    fun onEvent(event: CreatePlayerEvent) {
        when (event) {
            is CreatePlayerEvent.SavePlayer -> {
                viewModelScope.launch {
                    createPlayerUseCase.execute(event.name.trim())
                }
                _state.update { it.copy(name = "") }
            }
            is CreatePlayerEvent.ChangeNewPlayerName -> {
                _state.update { it.copy(name = event.name) }
            }
        }
    }
}