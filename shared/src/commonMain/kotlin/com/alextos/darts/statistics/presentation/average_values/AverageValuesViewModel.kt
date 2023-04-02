package com.alextos.darts.statistics.presentation.average_values

import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageSetScoreUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetPlayersAverageValuesUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageShotValueUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class AverageValuesViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getAverageSetScoreUseCase: GetAverageSetScoreUseCase,
    getAverageShotValueUseCase: GetAverageShotValueUseCase,
    getPlayersAverageValuesUseCase: GetPlayersAverageValuesUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(AverageValuesState())
    @OptIn(ExperimentalCoroutinesApi::class)
    val state = combine(
        _state,
        getPlayersUseCase.execute()
            .flatMapLatest { players ->
                getPlayersAverageValuesUseCase.execute(players)
            },
        getAverageSetScoreUseCase.execute(),
        getAverageShotValueUseCase.execute()
    ) { state, playersValues, averageSet, averageShot ->
            state.copy(
                averageSetOfAll = averageSet,
                averageShotOfAll = averageShot,
                playersAverageValues = playersValues
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AverageValuesState()
        )
        .toCommonStateFlow()
}