package com.alextos.darts.android.game.create_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.game.presentation.create_player.CreatePlayerEvent
import com.alextos.darts.game.presentation.create_player.CreatePlayerViewModel
import com.alextos.darts.players.domain.useCases.CreatePlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidCreatePlayerViewModel @Inject constructor(
    private val createPlayerUseCase: CreatePlayerUseCase
): ViewModel() {
    private val viewModel by lazy {
        CreatePlayerViewModel(createPlayerUseCase, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: CreatePlayerEvent) {
        viewModel.onEvent(event)
    }
}