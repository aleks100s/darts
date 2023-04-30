package com.alextos.darts.statistics.presentation.victory_distribution

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.victory_distribution.GetPlayerVictoryDistributionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class VictoryDistributionViewModel(
    getPlayerVictoryDistributionUseCase: GetPlayerVictoryDistributionUseCase,
    player: Player?,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VictoryDistributionState())
    val state = _state.combine(getPlayerVictoryDistributionUseCase.execute(player)) { state, distribution ->
        state.copy(distribution = distribution)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            VictoryDistributionState()
        )
        .toCommonStateFlow()
}