package com.alextos.darts.android.statistics.heatmap

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.use_cases.heatmap.GetSectorHeatmapUseCase
import com.alextos.darts.statistics.presentation.heatmap.SectorHeatmapViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AndroidSectorHeatmapViewModel @Inject constructor(
    getSectorHeatmapUseCase: GetSectorHeatmapUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val player: Player? = savedStateHandle.get<String>("player")?.let { Json.decodeFromString(it) }
        SectorHeatmapViewModel(
            getSectorHeatmapUseCase,
            player = player,
            viewModelScope
        )
    }

    val state = viewModel.state
}