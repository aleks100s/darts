package com.alextos.darts.android.statistics.best_turn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.best_turn.GetPlayersBestTurnsUseCase
import com.alextos.darts.statistics.presentation.best_set.BestTurnViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidBestTurnViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersBestTurnsUseCase: GetPlayersBestTurnsUseCase
): ViewModel() {
    private val viewModel = BestTurnViewModel(
        getPlayersUseCase,
        getPlayersBestTurnsUseCase,
        viewModelScope
    )

    val state = viewModel.state
}