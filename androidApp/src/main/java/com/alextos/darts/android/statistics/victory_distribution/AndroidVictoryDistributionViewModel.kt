package com.alextos.darts.android.statistics.victory_distribution

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.android.common.util.toPlayerList
import com.alextos.darts.statistics.domain.use_cases.victory_distribution.GetPlayerVictoryDistributionUseCase
import com.alextos.darts.statistics.presentation.victory_distribution.VictoryDistributionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidVictoryDistributionViewModel @Inject constructor(
    getPlayerVictoryDistributionUseCase: GetPlayerVictoryDistributionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val player = savedStateHandle.get<String>("player")?.toPlayerList()?.get(0)!!
        VictoryDistributionViewModel(
            getPlayerVictoryDistributionUseCase,
            player = player,
            viewModelScope
        )
    }

    val state = viewModel.state
}