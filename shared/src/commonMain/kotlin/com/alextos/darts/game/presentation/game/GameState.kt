package com.alextos.darts.game.presentation.game

import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.model.Set
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.models.GamePlayerResult

data class GameState(
    val gameHistory: List<PlayerHistory> = listOf(),
    val currentPlayer: Player? = null,
    val isGameFinished: Boolean = false,
    val isCloseGameDialogOpened: Boolean = false,
    val turnState: TurnState = TurnState.IsOngoing,
    val isInputVisible: Boolean = true,
    val gameGoal: Int = 0
) {
    fun isTurnOver(): Boolean {
        return when (turnState) {
            is TurnState.IsOver -> {
                true
            }
            else -> {
                false
            }
        }
    }

    fun turnResult(): Int {
        return when (turnState) {
            is TurnState.IsOver -> {
                turnState.result
            }
            else -> {
                0
            }
        }
    }
    fun currentResults(): List<GamePlayerResult> {
        return gameHistory.map { playerHistory ->
            GamePlayerResult(
                player = playerHistory.player,
                result = playerHistory.turns.lastOrNull()?.leftAfter ?: gameGoal,
                isCurrentPlayer = playerHistory.player == currentPlayer
            )
        }
    }

    fun currentPage(): Int = currentPlayer?.let { player ->
        gameHistory.map { it.player }.indexOf(player)
    } ?: 0

    fun getWinnerName(): String? {
        return if (gameHistory.count() == 1) {
            null
        } else {
            currentPlayer?.name
        }
    }

    fun getCurrentSet(): Set {
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
        return Set(
            shots = listOf(),
            leftAfter = leftAfter,
            isOverkill = false
        )
    }
}

sealed class TurnState {
    object IsOngoing: TurnState()
    data class IsOver(val result: Int): TurnState()
}