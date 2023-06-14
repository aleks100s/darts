package com.alextos.darts.game.presentation.calculator

import com.alextos.darts.core.domain.model.Turn

data class CalculatorState(
    val turns: List<Turn> = listOf()
) {
    fun currentTurn(): Turn {
        return turns.lastOrNull() ?: Turn(
            shots = listOf(),
            leftAfter = 0,
            isOverkill = false
        )
    }
}