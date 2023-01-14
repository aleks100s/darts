package com.alextos.darts.game.presentation.game

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.game.domain.models.Shot
import com.alextos.darts.game.domain.useCases.GameManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class GameViewModel(
    private val gameManager: GameManager,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state: Flow<GameState> = MutableStateFlow(GameState())

    val state = combine(_state, gameManager.gameHistory) { state, history ->
        state.copy(gameHistory = history)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GameState()
        )
        .toCommonFlow()

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.MakeShot -> {
                gameManager.makeShot(Shot(event.sector))
            }
        }
    }
}