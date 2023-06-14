package com.alextos.darts.game.domain.calculator

import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.domain.model.Turn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class CalculatorManager {
    private val _score = MutableStateFlow(0)
    val score: Flow<Int> = _score

    private val _shots = MutableStateFlow<List<Shot>>(listOf())
    val turns: Flow<List<Turn>> = _shots
        .map { shots ->
            val numberOfTurns = shots.count() / 3 + 1
            val turns = mutableListOf<Turn>()
            var sum = 0
            for (i in 0 until numberOfTurns) {
                val startIndex = i * 3
                var lastIndex = (i + 1) * 3
                if (i == numberOfTurns - 1) {
                    lastIndex = shots.lastIndex
                }
                val currentTurnShots = if (lastIndex == -1 || lastIndex < startIndex) {
                    listOf()
                } else {
                    shots.subList(startIndex, lastIndex)
                }
                sum += currentTurnShots.fold(0) { acc, shot -> acc + (shot.sector.value) }
                val turn = Turn(
                    shots = currentTurnShots,
                    leftAfter = sum ,
                    isOverkill = false
                )
                turns.add(turn)
            }
            turns
        }

    fun countShot(shot: Shot) {
        _shots.update { it.plus(shot) }
        _score.update { it + shot.sector.value }
    }

    fun undoLastShot() {
        _shots.value.lastOrNull()?.let { lastShot ->
            _shots.update { it.minus(lastShot) }
            _score.update { it - lastShot.sector.value }
        }
    }
}