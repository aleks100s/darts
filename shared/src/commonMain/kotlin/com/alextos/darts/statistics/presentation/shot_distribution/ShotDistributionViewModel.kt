package com.alextos.darts.statistics.presentation.shot_distribution

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.statistics.domain.use_cases.shot_distribution.GetPlayerShotDistributionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ShotDistributionViewModel(
    getPlayerShotDistributionUseCase: GetPlayerShotDistributionUseCase,
    player: Player,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ShotDistributionState())
    val state = _state.map { state ->
            state.copy(distribution = getPlayerShotDistributionUseCase.execute(player))
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ShotDistributionState()
        )
        .toCommonFlow()
}