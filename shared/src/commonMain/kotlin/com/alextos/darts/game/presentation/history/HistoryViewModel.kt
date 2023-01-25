package com.alextos.darts.game.presentation.history

import com.alextos.darts.core.util.toCommonFlow
import com.alextos.darts.game.domain.useCases.GetGameHistoryUseCase
import com.alextos.darts.core.domain.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class HistoryViewModel(
    getGameHistoryUseCase: GetGameHistoryUseCase,
    coroutineScope: CoroutineScope?,
    gameId: Long,
    players: List<Player>
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(HistoryState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HistoryState()
        )
        .toCommonFlow()

    init {
        val history = getGameHistoryUseCase.execute(gameId, players)
        _state.update { it.copy(gameHistory = history) }
    }

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.ShowProgress -> {
                _state.update { it.copy(isProgressVisible = true) }
            }
            is HistoryEvent.ShowHistory -> {
                _state.update { it.copy(isProgressVisible = false) }
            }
            else -> {}
        }
    }
}