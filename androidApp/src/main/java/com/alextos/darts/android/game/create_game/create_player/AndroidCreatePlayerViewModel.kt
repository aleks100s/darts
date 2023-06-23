package com.alextos.darts.android.game.create_game.create_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.game.presentation.create_player.CreatePlayerEvent
import com.alextos.darts.game.presentation.create_player.CreatePlayerViewModel
import com.alextos.darts.game.domain.useCases.CreatePlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidCreatePlayerViewModel @Inject constructor(
    createPlayerUseCase: CreatePlayerUseCase,
    getPlayerUseCase: GetPlayersUseCase
): ViewModel() {
    private val viewModel by lazy {
        CreatePlayerViewModel(createPlayerUseCase, getPlayerUseCase, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: CreatePlayerEvent) {
        viewModel.onEvent(event)
    }
}