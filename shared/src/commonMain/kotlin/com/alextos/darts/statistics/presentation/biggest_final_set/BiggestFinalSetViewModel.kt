package com.alextos.darts.statistics.presentation.biggest_final_set

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.game.domain.useCases.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.biggest_final_set.GetBiggestFinalSetUseCase
import com.alextos.darts.statistics.domain.use_cases.biggest_final_set.GetPlayerBiggestFinalSetUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class BiggestFinalSetViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getBiggestFinalSetUseCase: GetBiggestFinalSetUseCase,
    getPlayerBiggestFinalSetUseCase: GetPlayerBiggestFinalSetUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(BiggestFinalSetState())
    val state = getPlayersUseCase.execute()
        .map { players ->
            getPlayerBiggestFinalSetUseCase.execute(players)
        }
        .combine(_state) { playersSets, state ->
            state.copy(
                biggestFinalSetOfAll = getBiggestFinalSetUseCase.execute(),
                playersBiggestFinalSets = playersSets
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            BiggestFinalSetState()
        )
        .toCommonFlow()
}