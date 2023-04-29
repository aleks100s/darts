package com.alextos.darts.android.game.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.android.common.util.toPlayerList
import com.alextos.darts.game.domain.useCases.GetGameHistoryUseCase
import com.alextos.darts.game.presentation.history.HistoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidHistoryViewModel @Inject constructor(
    getGameHistoryUseCase: GetGameHistoryUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val players = savedStateHandle.get<String>("list")?.toPlayerList() ?: listOf()
        val gameId = savedStateHandle.get<String>("gameId")?.toLong() ?: 0
        val gameGoal = savedStateHandle.get<String>("gameGoal")?.toInt() ?: 0
        HistoryViewModel(
            getGameHistoryUseCase,
            coroutineScope = viewModelScope,
            gameId = gameId,
            gameGoal = gameGoal,
            players = players
        )
    }

    val state = viewModel.state
}