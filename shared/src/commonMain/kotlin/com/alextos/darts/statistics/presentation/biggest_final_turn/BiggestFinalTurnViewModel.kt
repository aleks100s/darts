package com.alextos.darts.statistics.presentation.biggest_final_turn

import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.biggest_final_turn.GetPlayersBiggestFinalTurnUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class BiggestFinalTurnViewModel(
    getPlayersUseCase: GetPlayersUseCase,
    getPlayersBiggestFinalTurnUseCase: GetPlayersBiggestFinalTurnUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(BiggestFinalTurnState())
    @OptIn(ExperimentalCoroutinesApi::class)
    val state = _state
        .combine(
            getPlayersUseCase.execute()
                .flatMapLatest { players ->
                    getPlayersBiggestFinalTurnUseCase.execute(players)
                }
        ) { state, playersTurns ->
            state.copy(playersBiggestFinalTurns = playersTurns, isLoading = false)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            BiggestFinalTurnState()
        )
        .toCommonStateFlow()
}