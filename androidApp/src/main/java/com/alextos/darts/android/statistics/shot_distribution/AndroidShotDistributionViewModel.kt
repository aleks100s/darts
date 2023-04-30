package com.alextos.darts.android.statistics.shot_distribution

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.use_cases.shot_distribution.GetPlayerShotDistributionUseCase
import com.alextos.darts.statistics.presentation.shot_distribution.ShotDistributionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AndroidShotDistributionViewModel @Inject constructor(
    getPlayerShotDistributionUseCase: GetPlayerShotDistributionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val player: Player? = savedStateHandle.get<String>("player")?.let { Json.decodeFromString(it) }
        ShotDistributionViewModel(
            getPlayerShotDistributionUseCase,
            player = player,
            viewModelScope
        )
    }

    val state = viewModel.state
}