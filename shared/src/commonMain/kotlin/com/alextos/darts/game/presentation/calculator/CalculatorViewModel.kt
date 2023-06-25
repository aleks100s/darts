package com.alextos.darts.game.presentation.calculator

import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.game.domain.calculator.CalculatorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CalculatorViewModel(
    private val calculatorManager: CalculatorManager,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(CalculatorState())

    val state = combine(
        _state,
        calculatorManager.turns
    ) { state, turns ->
        state.copy(turns = turns)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CalculatorState()
        )
        .toCommonStateFlow()

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.MakeShot -> {
                calculatorManager.countShot(Shot(event.sector))
            }
            is CalculatorEvent.UndoLastShot -> {
                calculatorManager.undoLastShot()
            }
        }
    }
}