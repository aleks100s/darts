package com.alextos.darts.game.presentation.create_player

import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.game.domain.useCases.CreatePlayerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

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
        .toCommonFlow()

    fun onEvent(event: CreatePlayerEvent) {
        when (event) {
            is CreatePlayerEvent.SavePlayer -> {
                createPlayerUseCase.execute(event.name.trim())
                _state.update { it.copy(name = "") }
            }
            is CreatePlayerEvent.ChangeNewPlayerName -> {
                _state.update { it.copy(name = event.name) }
            }
        }
    }
}