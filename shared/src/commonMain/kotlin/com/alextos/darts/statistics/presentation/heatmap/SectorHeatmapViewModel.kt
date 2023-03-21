package com.alextos.darts.statistics.presentation.heatmap

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.statistics.domain.use_cases.heatmap.GetSectorHeatmapUseCase
import com.alextos.darts.statistics.presentation.shot_distribution.ShotDistributionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SectorHeatmapViewModel(
    getSectorHeatmapUseCase: GetSectorHeatmapUseCase,
    player: Player,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(SectorHeatmapState())
    val state = _state.map { state ->
        state.copy(distribution = getSectorHeatmapUseCase.execute(player))
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SectorHeatmapState()
        )
        .toCommonFlow()
}