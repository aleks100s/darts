package com.alextos.darts.game.presentation.calculator

import com.alextos.darts.core.domain.model.Turn

data class CalculatorState(
    val turns: List<Turn> = listOf(),
    val score: Int = 0
) {
    fun currentTurn(): Turn {
        return turns.lastOrNull() ?: Turn(
            shots = listOf(),
            leftAfter = 0,
            isOverkill = false
        )
    }
}