package com.alextos.darts.statistics.presentation.time

import com.alextos.darts.core.domain.useCases.GetPlayersUseCase
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.time.GetGlobalTotalTimePlayedUseCase
import com.alextos.darts.statistics.domain.use_cases.time.GetPlayersTotalTimePlayedUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class TimeViewModel(
    getGlobalTotalTimePlayedUseCase: GetGlobalTotalTimePlayedUseCase,
    getPlayersTotalTimePlayedUseCase: GetPlayersTotalTimePlayedUseCase,
    getPlayersUseCase: GetPlayersUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(TimeState())

    val state = combine(
        _state,
        getGlobalTotalTimePlayedUseCase.execute(),
        getPlayersUseCase.execute()
            .flatMapLatest { players ->
                getPlayersTotalTimePlayedUseCase.execute(players)
            }
    ) { state, totalTime, playersDuration ->
        state.copy(
            totalTimePlayed = totalTime,
            playersDuration = playersDuration,
            isLoading = false
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TimeState()
        )
        .toCommonStateFlow()
}