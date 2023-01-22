package com.alextos.darts.android.statistics.average_values

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageSetScoreUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetPlayersAverageValuesUseCase
import com.alextos.darts.statistics.domain.use_cases.average_values.GetAverageShotValueUseCase
import com.alextos.darts.statistics.presentation.average_values.AverageValuesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidAverageValuesViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    getAverageSetScoreUseCase: GetAverageSetScoreUseCase,
    getAverageShotValueUseCase: GetAverageShotValueUseCase,
    getPlayersAverageValuesUseCase: GetPlayersAverageValuesUseCase
): ViewModel() {
    private val viewModel = AverageValuesViewModel(
        getPlayersUseCase,
        getAverageSetScoreUseCase,
        getAverageShotValueUseCase,
        getPlayersAverageValuesUseCase,
        viewModelScope
    )

    val state = viewModel.state
}