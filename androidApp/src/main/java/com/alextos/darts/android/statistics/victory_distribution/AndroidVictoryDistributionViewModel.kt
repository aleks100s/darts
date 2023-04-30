package com.alextos.darts.android.statistics.victory_distribution

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.use_cases.victory_distribution.GetPlayerVictoryDistributionUseCase
import com.alextos.darts.statistics.presentation.victory_distribution.VictoryDistributionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AndroidVictoryDistributionViewModel @Inject constructor(
    getPlayerVictoryDistributionUseCase: GetPlayerVictoryDistributionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val player: Player? = savedStateHandle.get<String>("player")?.let { Json.decodeFromString(it) }
        VictoryDistributionViewModel(
            getPlayerVictoryDistributionUseCase,
            player = player,
            viewModelScope
        )
    }

    val state = viewModel.state
}