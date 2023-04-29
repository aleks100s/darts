package com.alextos.darts.game.presentation.history

import com.alextos.darts.game.domain.useCases.GetGameHistoryUseCase
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.util.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class HistoryViewModel(
    getGameHistoryUseCase: GetGameHistoryUseCase,
    coroutineScope: CoroutineScope?,
    gameId: Long,
    gameGoal: Int,
    players: List<Player>
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(HistoryState(gameGoal = gameGoal))
    val state = _state
        .combine(getGameHistoryUseCase.execute(gameId, players)) { state, history ->
            state.copy(gameHistory = history)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HistoryState()
        )
        .toCommonStateFlow()
}