package com.alextos.darts.statistics.presentation.best_set

import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.best_turn.GetPlayersBestTurnsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class BestTurnViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersBestTurnsUseCase: GetPlayersBestTurnsUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(BestTurnState())
    val state = _state
        .combine(
            getPlayersUseCase.execute()
                .flatMapLatest { players ->
                    getPlayersBestTurnsUseCase.execute(players)
                }
        ) { state, playersTurns ->
            state.copy(playersBestTurns = playersTurns)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            BestTurnState()
        )
        .toCommonStateFlow()
}