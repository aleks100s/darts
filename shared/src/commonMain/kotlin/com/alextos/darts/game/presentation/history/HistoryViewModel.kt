package com.alextos.darts.game.presentation.history

import com.alextos.darts.game.domain.useCases.GetGameHistoryFlowUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class HistoryViewModel(
    getGameHistoryUseCase: GetGameHistoryFlowUseCase,
    coroutineScope: CoroutineScope?,
    game: Game?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(HistoryState(gameGoal = game?.gameGoal ?: 0, duration = game?.duration ?: GameDuration(0, 0)))
    val state = _state
        .combine(getGameHistoryUseCase.execute(game?.id ?: 0, game?.players ?: listOf())) { state, history ->
            state.copy(gameHistory = history, isLoading = false)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HistoryState()
        )
        .toCommonStateFlow()
}