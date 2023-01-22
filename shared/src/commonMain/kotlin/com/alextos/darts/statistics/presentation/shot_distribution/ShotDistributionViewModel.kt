package com.alextos.darts.statistics.presentation.shot_distribution

import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.statistics.domain.use_cases.shot_distribution.GetPlayersShotDistributionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ShotDistributionViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersShotDistributionUseCase: GetPlayersShotDistributionUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ShotDistributionState())
    val state = getPlayersUseCase.execute()
        .map { players ->
            getPlayersShotDistributionUseCase.execute(players)
        }
        .combine(_state) { playersDistribution, state ->
            state.copy(
                playersDistribution = playersDistribution
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ShotDistributionState()
        )
        .toCommonFlow()
}