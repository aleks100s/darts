package com.alextos.darts.android.statistics.most_frequent_shots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.GetPlayersUseCase
import com.alextos.darts.statistics.domain.use_cases.most_frequent_shots.GetMostFrequentShotsUseCase
import com.alextos.darts.statistics.domain.use_cases.most_frequent_shots.GetPlayersMostFrequentShotsUseCase
import com.alextos.darts.statistics.presentation.most_frequent_shots.MostFrequentShotsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidMostFrequentShotsViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    getMostFrequentShotsUseCase: GetMostFrequentShotsUseCase,
    getPlayersMostFrequentShotsUseCase: GetPlayersMostFrequentShotsUseCase
): ViewModel() {
    private val viewModel = MostFrequentShotsViewModel(
        getPlayersUseCase,
        getMostFrequentShotsUseCase,
        getPlayersMostFrequentShotsUseCase,
        viewModelScope
    )

    val state = viewModel.state
}