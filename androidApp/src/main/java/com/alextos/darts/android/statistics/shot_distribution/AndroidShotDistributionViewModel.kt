package com.alextos.darts.android.statistics.shot_distribution

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.android.common.util.toPlayerList
import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.shot_distribution.GetPlayerShotDistributionUseCase
import com.alextos.darts.statistics.presentation.shot_distribution.ShotDistributionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidShotDistributionViewModel @Inject constructor(
    getPlayerShotDistributionUseCase: GetPlayerShotDistributionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val player = savedStateHandle.get<String>("player")?.toPlayerList()?.get(0)!!
        ShotDistributionViewModel(
            getPlayerShotDistributionUseCase,
            player = player,
            viewModelScope
        )
    }

    val state = viewModel.state
}