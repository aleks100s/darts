package com.alextos.darts.android.statistics.heatmap

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.android.common.util.toPlayerList
import com.alextos.darts.statistics.domain.use_cases.heatmap.GetSectorHeatmapUseCase
import com.alextos.darts.statistics.presentation.heatmap.SectorHeatmapViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidSectorHeatmapViewModel @Inject constructor(
    getSectorHeatmapUseCase: GetSectorHeatmapUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val player = savedStateHandle.get<String>("player")?.toPlayerList()?.get(0)!!
        SectorHeatmapViewModel(
            getSectorHeatmapUseCase,
            player = player,
            viewModelScope
        )
    }

    val state = viewModel.state
}