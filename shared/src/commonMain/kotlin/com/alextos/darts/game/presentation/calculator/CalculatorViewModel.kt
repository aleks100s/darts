package com.alextos.darts.game.presentation.calculator

import com.alextos.darts.core.util.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn

class CalculatorViewModel(
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(CalculatorState())

    val state = _state.combine(emptyFlow<String>()) { state, _ ->
        state
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CalculatorState()
        )
        .toCommonStateFlow()

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.BackButtonPressed -> {}
            is CalculatorEvent.MakeShot -> {

            }
        }
    }
}