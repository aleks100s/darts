package com.alextos.darts.android.game.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.game.domain.useCases.GameManager
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidGameViewModel @Inject constructor(
    private val gameManager: GameManager
): ViewModel() {
    private val viewModel by lazy {
        GameViewModel(gameManager, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: GameEvent) {
        viewModel.onEvent(event)
    }
}