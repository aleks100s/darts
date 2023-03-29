package com.alextos.darts.android.game.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.android.common.util.toPlayerList
import com.alextos.darts.game.domain.useCases.GameManager
import com.alextos.darts.game.domain.useCases.SaveGameHistoryUseCase
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidGameViewModel @Inject constructor(
    saveGameHistoryUseCase: SaveGameHistoryUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val players = savedStateHandle.get<String>("list")?.toPlayerList() ?: listOf()
        val goal = savedStateHandle.get<String>("goal")?.toInt() ?: 0
        val finishWithDoubles = savedStateHandle.get<Boolean>("doubles") ?: false
        val gameManager = GameManager(
            saveGameHistoryUseCase,
            players,
            goal,
            finishWithDoubles
        )
        GameViewModel(gameManager, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: GameEvent) {
        viewModel.onEvent(event)
    }
}