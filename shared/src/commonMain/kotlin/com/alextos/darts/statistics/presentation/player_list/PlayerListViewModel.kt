package com.alextos.darts.statistics.presentation.player_list

import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class PlayerListViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(PlayerListState())
    val state = getPlayersUseCase.execute()
        .combine(_state) { players, state ->
            state.copy(players = players, isLoading = false)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PlayerListState()
        )
        .toCommonStateFlow()
}