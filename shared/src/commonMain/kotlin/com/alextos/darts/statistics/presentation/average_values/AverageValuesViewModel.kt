package com.alextos.darts.statistics.presentation.average_values

import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageTurnScoreUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetPlayersAverageValuesUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageShotValueUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class AverageValuesViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getAverageTurnScoreUseCase: GetAverageTurnScoreUseCase,
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
        getAverageTurnScoreUseCase.execute(),
        getAverageShotValueUseCase.execute()
    ) { state, playersValues, averageSet, averageShot ->
            state.copy(
                averageTurnOfAll = averageSet,
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