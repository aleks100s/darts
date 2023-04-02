package com.alextos.darts.statistics.presentation.best_set

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.best_set.GetPlayersBestSetsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class BestSetViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersBestSetsUseCase: GetPlayersBestSetsUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(BestSetState())
    val state = getPlayersUseCase.execute()
        .map { players ->
            getPlayersBestSetsUseCase.execute(players)
        }
        .combine(_state) { playersSets, state ->
            state.copy(playersBestSets = playersSets)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            BestSetState()
        )
        .toCommonStateFlow()
}