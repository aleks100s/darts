package com.alextos.darts.statistics.presentation.shot_distribution

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.shot_distribution.GetPlayerShotDistributionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ShotDistributionViewModel(
    getPlayerShotDistributionUseCase: GetPlayerShotDistributionUseCase,
    player: Player?,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ShotDistributionState())
    val state = _state
        .combine(getPlayerShotDistributionUseCase.execute(player)) { state, distribution ->
            state.copy(distribution = distribution)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ShotDistributionState()
        )
        .toCommonStateFlow()
}