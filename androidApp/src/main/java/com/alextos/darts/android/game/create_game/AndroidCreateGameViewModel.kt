package com.alextos.darts.android.game.create_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.core.domain.useCases.DeletePlayerUseCase
import com.alextos.darts.game.presentation.create_game.CreateGameEvent
import com.alextos.darts.game.presentation.create_game.CreateGameViewModel
import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidCreateGameViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase,
    deletePlayerUseCase: DeletePlayerUseCase
): ViewModel() {
    private val viewModel by lazy {
        CreateGameViewModel(deletePlayerUseCase, getPlayersUseCase, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: CreateGameEvent) {
        viewModel.onEvent(event)
    }
}