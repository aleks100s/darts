package com.alextos.darts.game.presentation.game

import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.Set
import com.alextos.darts.core.domain.Player
import com.alextos.darts.game.domain.models.GamePlayerResult

data class GameState(
    val gameHistory: List<PlayerHistory> = listOf(),
    val currentPlayer: Player? = null,
    val isGameFinished: Boolean = false,
    val isCloseGameDialogOpened: Boolean = false,
    val turnState: TurnState = TurnState.IsOngoing,
    val isInputVisible: Boolean = false,
    val gameGoal: Int = 0
) {
    fun currentResults(): List<GamePlayerResult> {
        return gameHistory.map { playerHistory ->
            GamePlayerResult(
                player = playerHistory.player,
                result = playerHistory.turns.lastOrNull()?.leftAfter ?: 0,
                isCurrentPlayer = playerHistory.player == currentPlayer
            )
        }
    }

    fun currentPage(): Int = currentPlayer?.let { player ->
        gameHistory.map { it.player }.indexOf(player)
    } ?: 0

    fun getWinnerName() = currentPlayer?.name

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