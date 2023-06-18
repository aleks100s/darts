package com.alextos.darts.android.statistics.time

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.time.GetGlobalTotalTimePlayedUseCase
import com.alextos.darts.statistics.domain.use_cases.time.GetPlayersTotalTimePlayedUseCase
import com.alextos.darts.statistics.presentation.time.TimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTimeViewModel @Inject constructor(
    getGlobalTotalTimePlayedUseCase: GetGlobalTotalTimePlayedUseCase,
    getPlayersTotalTimePlayedUseCase: GetPlayersTotalTimePlayedUseCase,
    getPlayersUseCase: GetPlayersUseCase
): ViewModel() {
    private val viewModel = TimeViewModel(
        getGlobalTotalTimePlayedUseCase,
        getPlayersTotalTimePlayedUseCase,
        getPlayersUseCase,
        viewModelScope
    )
    val state = viewModel.state
}