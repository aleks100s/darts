package com.alextos.darts.game.presentation.history

import com.alextos.darts.game.domain.models.PlayerGameValue
import com.alextos.darts.game.domain.models.PlayerHistory

data class HistoryState(
    val gameHistory: List<PlayerHistory> = listOf(),
    val isRecapVisible: Boolean = false,
    val gameGoal: Int = 0
) {
    fun averageSets(): List<PlayerGameValue> {
        return gameHistory.map { playerHistory ->
            val sum =  playerHistory.turns.sumOf { it.score() }
            val count = playerHistory.turns.count()
            if (count != 0) {
                PlayerGameValue(playerHistory.player, sum / count)
            } else {
                PlayerGameValue(playerHistory.player, gameGoal)
            }
        }
    }

    fun biggestSets(): List<PlayerGameValue> {
        return gameHistory.map { playerHistory ->
            PlayerGameValue(playerHistory.player, playerHistory.biggestSet()?.score() ?: 0)
        }
    }

    fun smallestSets(): List<PlayerGameValue> {
        return gameHistory.map { playerHistory ->
            PlayerGameValue(playerHistory.player, playerHistory.smallestSet()?.score() ?: 0)
        }
    }

    fun misses(): List<PlayerGameValue> {
        return gameHistory.map { playerHistory ->
            PlayerGameValue(playerHistory.player, playerHistory.numberOfMisses())
        }
    }

    fun overkills(): List<PlayerGameValue> {
        return gameHistory.map { playerHistory ->
            PlayerGameValue(playerHistory.player, playerHistory.numberOfOverkills())
        }
    }
}
