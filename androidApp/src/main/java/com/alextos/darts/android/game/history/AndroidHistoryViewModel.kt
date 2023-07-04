package com.alextos.darts.android.game.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.useCases.GetGameHistoryFlowUseCase
import com.alextos.darts.game.presentation.history.HistoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AndroidHistoryViewModel @Inject constructor(
    getGameHistoryFlowUseCase: GetGameHistoryFlowUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val game: Game? = savedStateHandle.get<String>("game")?.let { Json.decodeFromString(it) }
        HistoryViewModel(
            getGameHistoryFlowUseCase,
            coroutineScope = viewModelScope,
            game = game
        )
    }

    val state = viewModel.state
}