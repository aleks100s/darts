package com.alextos.darts.statistics.presentation.heatmap

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.heatmap.GetSectorHeatmapUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class SectorHeatmapViewModel(
    getSectorHeatmapUseCase: GetSectorHeatmapUseCase,
    player: Player?,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(SectorHeatmapState())
    val state = _state.combine(getSectorHeatmapUseCase.execute(player)) { state, distribution ->
        state.copy(distribution = distribution)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SectorHeatmapState()
        )
        .toCommonStateFlow()
}