package com.alextos.darts.game.presentation.history

import com.alextos.darts.game.domain.models.PlayerGameValue
import com.alextos.darts.game.domain.models.PlayerHistory
import kotlinx.serialization.Serializable

@Serializable
data class HistoryState(
    val gameHistory: List<PlayerHistory> = listOf(),
    val gameGoal: Int = 0
) {
    fun averageTurns(): List<PlayerGameValue> {
        return gameHistory.map { playerHistory ->
            PlayerGameValue(playerHistory.player, playerHistory.average())
        }
    }

    fun biggestTurns(): List<PlayerGameValue> {
        return gameHistory.map { playerHistory ->
            PlayerGameValue(playerHistory.player, playerHistory.biggestTurn()?.score() ?: 0)
        }
    }

    fun smallestTurns(): List<PlayerGameValue> {
        return gameHistory.map { playerHistory ->
            PlayerGameValue(playerHistory.player, playerHistory.smallestTurn()?.score() ?: 0)
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
