package com.alextos.darts.game.presentation.game

import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.models.GamePlayerResult
import com.alextos.darts.game.domain.models.PlayerGameValue
import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val gameHistory: List<PlayerHistory> = listOf(),
    val currentPlayer: Player? = null,
    val isCloseGameDialogOpened: Boolean = false,
    val turnState: TurnState = TurnState.IsOngoing,
    val gameGoal: Int = 0,
    val averageTurns: List<PlayerGameValue> = listOf(),
    val isStatisticsEnabled: Boolean = true,
    val gameResult: GameResult? = null,
    val turnNumber: Int = 0
) {
    fun isTurnOver(): Boolean {
        return when (turnState) {
            is TurnState.IsOver -> { true }
            else -> { false }
        }
    }

    fun turnResult(): Int {
        return when (turnState) {
            is TurnState.IsOver -> { turnState.result }
            else -> { 0 }
        }
    }

    fun currentResults(): List<GamePlayerResult> {
        return gameHistory.map { playerHistory ->
            val lastTurn = playerHistory.turns.lastOrNull()
            return@map GamePlayerResult(
                player = playerHistory.player,
                score = lastTurn?.leftAfter ?: gameGoal,
                turnAverage = playerHistory.average(),
                overallTurnAverage = averageTurns.find { it.player == playerHistory.player }?.value ?: 0,
                isCurrentPlayer = playerHistory.player == currentPlayer
            )
        }
    }

    fun currentPage(): Int = currentPlayer?.let { player ->
        gameHistory.map { it.player }.indexOf(player)
    } ?: 0

    fun getCurrentTurn(): Turn {
        var leftAfter = 0
        currentPlayer?.let { player ->
            gameHistory.firstOrNull { it.player == player }?.let { history ->
                history.turns.lastOrNull()?.let { set ->
                    leftAfter = set.leftAfter
                    if (turnState is TurnState.IsOver || set.shots.count() < 3) {
                        return set
                    }
                } ?: kotlin.run {
                    leftAfter = gameGoal
                }
            }
        }
        return Turn(
            shots = listOf(),
            leftAfter = leftAfter,
            isOverkill = false
        )
    }
}