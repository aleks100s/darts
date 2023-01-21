package com.alextos.darts.android.statistics.best_set

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.game.domain.useCases.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.GetBestSetUseCase
import com.alextos.darts.statistics.domain.use_cases.GetPlayersBestSetsUseCase
import com.alextos.darts.statistics.presentation.best_set.BestSetViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidBestSetViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    getBestSetUseCase: GetBestSetUseCase,
    getPlayersBestSetsUseCase: GetPlayersBestSetsUseCase
): ViewModel() {
    private val viewModel = BestSetViewModel(
        getPlayersUseCase,
        getBestSetUseCase,
        getPlayersBestSetsUseCase,
        viewModelScope
    )

    val state = viewModel.state
}