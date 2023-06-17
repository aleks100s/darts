package com.alextos.darts.statistics.presentation.time

import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.statistics.domain.use_cases.time.GetGlobalTotalTimePlayedUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class TimeViewModel(
    getGlobalTotalTimePlayedUseCase: GetGlobalTotalTimePlayedUseCase,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(TimeState())

    val state = combine(
        _state,
        getGlobalTotalTimePlayedUseCase.execute()
    ) { state, totalTime ->
        state.copy(
            totalTimePlayed = totalTime,
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