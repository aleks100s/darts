package com.alextos.darts.statistics.presentation.biggest_final_set

import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.biggest_final_set.GetPlayersBiggestFinalSetUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class BiggestFinalSetViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersBiggestFinalSetUseCase: GetPlayersBiggestFinalSetUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(BiggestFinalSetState())
    @OptIn(ExperimentalCoroutinesApi::class)
    val state = _state
        .combine(
            getPlayersUseCase.execute()
                .flatMapLatest { players ->
                    getPlayersBiggestFinalSetUseCase.execute(players)
                }
        ) { state, playersSets ->
            state.copy(playersBiggestFinalSets = playersSets)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            BiggestFinalSetState()
        )
        .toCommonStateFlow()
}