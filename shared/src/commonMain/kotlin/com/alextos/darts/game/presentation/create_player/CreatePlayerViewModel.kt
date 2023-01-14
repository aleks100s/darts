package com.alextos.darts.game.presentation.create_player

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.players.domain.useCases.CreatePlayerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CreatePlayerViewModel(
    private val createPlayerUseCase: CreatePlayerUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(CreatePlayerState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CreatePlayerState()
    )
        .toCommonFlow()

    fun onEvent(event: CreatePlayerEvent) {
        when (event) {
            is CreatePlayerEvent.SavePlayer -> {
                createPlayerUseCase.execute(event.name)
            }
            is CreatePlayerEvent.ChangeNewPlayerName -> {
                _state.update { it.copy(name = event.name) }
            }
        }
    }
}