package com.alextos.darts.android.statistics.average_values

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageTurnScoreUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetPlayersAverageValuesUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageShotValueUseCase
import com.alextos.darts.statistics.presentation.average_values.AverageValuesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidAverageValuesViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    getAverageTurnScoreUseCase: GetAverageTurnScoreUseCase,
    getAverageShotValueUseCase: GetAverageShotValueUseCase,
    getPlayersAverageValuesUseCase: GetPlayersAverageValuesUseCase
): ViewModel() {
    private val viewModel = AverageValuesViewModel(
        getPlayersUseCase,
        getAverageTurnScoreUseCase,
        getAverageShotValueUseCase,
        getPlayersAverageValuesUseCase,
        viewModelScope
    )

    val state = viewModel.state
}