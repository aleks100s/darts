package com.alextos.darts.android.statistics.shot_distribution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.shot_distribution.GetPlayersShotDistributionUseCase
import com.alextos.darts.statistics.presentation.shot_distribution.ShotDistributionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidShotDistributionViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersShotDistributionUseCase: GetPlayersShotDistributionUseCase
): ViewModel() {
    private val viewModel = ShotDistributionViewModel(
        getPlayersUseCase,
        getPlayersShotDistributionUseCase,
        viewModelScope
    )

    val state = viewModel.state
}