package com.alextos.darts.android.statistics.biggest_final_set

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.biggest_final_set.GetPlayersBiggestFinalSetUseCase
import com.alextos.darts.statistics.presentation.biggest_final_set.BiggestFinalSetViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidBiggestFinalSetViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersBiggestFinalSetUseCase: GetPlayersBiggestFinalSetUseCase
): ViewModel() {
    private val viewModel = BiggestFinalSetViewModel(
        getPlayersUseCase,
        getPlayersBiggestFinalSetUseCase,
        viewModelScope
    )

    val state = viewModel.state
}