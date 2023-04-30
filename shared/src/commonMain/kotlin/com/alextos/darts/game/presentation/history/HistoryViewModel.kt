package com.alextos.darts.game.presentation.history

import com.alextos.darts.game.domain.useCases.GetGameHistoryUseCase
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.game.domain.models.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class HistoryViewModel(
    getGameHistoryUseCase: GetGameHistoryUseCase,
    coroutineScope: CoroutineScope?,
    game: Game?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(HistoryState(gameGoal = game?.gameGoal ?: 0))
    val state = _state
        .combine(getGameHistoryUseCase.execute(game?.id ?: 0, game?.players ?: listOf())) { state, history ->
            state.copy(gameHistory = history)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HistoryState()
        )
        .toCommonStateFlow()
}