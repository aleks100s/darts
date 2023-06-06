package com.alextos.darts.android.game.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.darts.game.domain.game_manager.GameManager
import com.alextos.darts.game.domain.models.GameSettings
import com.alextos.darts.game.domain.useCases.GetPlayerAverageTurnUseCase
import com.alextos.darts.game.domain.useCases.SaveGameHistoryUseCase
import com.alextos.darts.game.presentation.game.GameEvent
import com.alextos.darts.game.presentation.game.GameViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AndroidGameViewModel @Inject constructor(
    saveGameHistoryUseCase: SaveGameHistoryUseCase,
    getPlayerAverageTurnUseCase: GetPlayerAverageTurnUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val viewModel by lazy {
        val settings: GameSettings? = savedStateHandle.get<String>("settings")?.let { Json.decodeFromString(it) }
        val gameManager = GameManager(
            saveGameHistoryUseCase,
            getPlayerAverageTurnUseCase,
            settings
        )
        GameViewModel(gameManager, settings, viewModelScope)
    }

    val state = viewModel.state

    fun onEvent(event: GameEvent) {
        viewModel.onEvent(event)
    }
}