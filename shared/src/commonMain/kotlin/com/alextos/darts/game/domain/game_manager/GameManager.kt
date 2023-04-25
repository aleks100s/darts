package com.alextos.darts.game.domain.game_manager

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.game.domain.useCases.SaveGameHistoryUseCase
import com.alextos.darts.game.presentation.game.TurnState
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GameManager(
    private val saveGameHistoryUseCase: SaveGameHistoryUseCase,
    private val players: List<Player>,
    private val goal: Int,
    private val finishWithDoubles: Boolean
) {
    private val playerHistoryManagers by lazy {
        players.map {
            PlayerHistoryManager(it, goal, finishWithDoubles)
        }
    }

    private val _turnState: MutableStateFlow<TurnState> = MutableStateFlow(TurnState.IsOngoing)
    val turnState: StateFlow<TurnState> = _turnState

    private val _currentPlayer = MutableStateFlow(players[0])
    val currentPlayer: StateFlow<Player> = _currentPlayer

    private val _isGameFinished = MutableStateFlow(false)
    val isGameFinished: StateFlow<Boolean> = _isGameFinished

    val gameHistory = combine(playerHistoryManagers.map { it.playerHistory }) { array ->
        array.toList()
    }

    fun getGoal(): Int = goal

    fun makeShot(shot: Shot) {
        if (_isGameFinished.value) {
            return
        }
        val trackUserHistoryUseCase = currentPlayerHistoryManager()
        val turnState = trackUserHistoryUseCase.makeShot(shot)
        _turnState.update { turnState }
    }

    fun resetTurn() {
        _turnState.update { TurnState.IsOngoing }
        currentPlayerHistoryManager().resetCurrentTurn()
    }

    suspend fun changeTurn() {
        _turnState.update { TurnState.IsOngoing }
        if (currentPlayerHistoryManager().isGameOver()) {
            finishGame()
        } else {
            nextTurn()
        }
    }

    fun eraseShot() {
        currentPlayerHistoryManager().undoLastShot()
    }

    private suspend fun finishGame() {
        val winner = if (players.count() == 1) null else currentPlayer.value
        val game = Game(
            players = players,
            winner = winner,
            gameGoal = goal,
            timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
        val gameHistory = GameHistory(
            game = game,
            playerHistoryManagers.map { it.playerHistory.value }.sortedBy {
                game.players.indexOf(it.player)
            }
        )
        saveGameHistoryUseCase.execute(gameHistory = gameHistory)
        _isGameFinished.update { true }
    }

    private fun currentPlayerHistoryManager(): PlayerHistoryManager {
        return playerHistoryManagers[players.indexOf(currentPlayer.value)]
    }

    private fun nextTurn() {
        currentPlayerHistoryManager().finishTurn()
        val index = players.indexOf(currentPlayer.value)
        val nextPlayer = players.getOrNull(index + 1) ?: run {
            players[0]
        }
        _currentPlayer.update { nextPlayer }
    }
}