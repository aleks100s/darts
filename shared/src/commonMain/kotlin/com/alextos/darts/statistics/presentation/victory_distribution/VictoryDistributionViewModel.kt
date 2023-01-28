package com.alextos.darts.statistics.presentation.victory_distribution

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.statistics.domain.use_cases.victory_distribution.GetPlayerVictoryDistributionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class VictoryDistributionViewModel(
    getPlayerVictoryDistributionUseCase: GetPlayerVictoryDistributionUseCase,
    player: Player,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VictoryDistributionState())
    val state = _state.map { state ->
        state.copy(distribution = getPlayerVictoryDistributionUseCase.execute(player))
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            VictoryDistributionState()
        )
        .toCommonFlow()
}