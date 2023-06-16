package com.alextos.darts.game.presentation.calculator

import com.alextos.darts.core.domain.model.Turn

data class CalculatorState(
    val turns: List<Turn> = listOf()
) {
    val score: Int
        get() { return turns.fold(0) { acc, turn -> acc + turn.score() } }

    val turnNumber: Int
        get() { return turns.count { it.shots.isNotEmpty() } }

    val lastTurn: Turn
        get() {
            return turns.lastOrNull { it.shots.isNotEmpty() }
                ?: Turn(shots = listOf(), leftAfter = 0, isOverkill = false)
        }

    val average: Int?
        get() {
            if (turnNumber == 0) {
                return null
            }

            return score / turnNumber
        }
}