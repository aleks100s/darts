package com.alextos.darts.game.domain.useCases

import com.alextos.darts.core.domain.Player
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.core.domain.Shot
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
    private val evaluateShotUseCases by lazy {
        players.map {
            EvaluateShotUseCase(goal, finishWithDoubles)
        }
    }

    private val trackUserHistoryUseCases by lazy {
        players.map {
            TrackUserHistoryUseCase(it)
        }
    }

    private val _turnState: MutableStateFlow<TurnState> = MutableStateFlow(TurnState.IsOngoing)
    val turnState: StateFlow<TurnState> = _turnState

    private val _currentPlayer = MutableStateFlow(players[0])
    val currentPlayer: StateFlow<Player> = _currentPlayer

    private val _isGameFinished = MutableStateFlow(false)
    val isGameFinished: StateFlow<Boolean> = _isGameFinished

    val gameHistory = combine(trackUserHistoryUseCases.map { it.playerHistory }) { array ->
        array.toList()
    }

    fun getGoal(): Int = goal

    fun makeShot(shot: Shot) {
        if (_isGameFinished.value) {
            return
        }
        val evaluateShotUseCase = currentEvaluateShotUseCase()
        val result = evaluateShotUseCase.checkShot(shot)
        val trackUserHistoryUseCase = currentTrackUseCaseUseCase()
        val isSetOver = trackUserHistoryUseCase.trackShotResult(result)
        if (isSetOver) {
            _turnState.update { TurnState.IsOver(trackUserHistoryUseCase.currentTurnResult()) }
        }
    }

    fun resetTurn() {
        _turnState.update { TurnState.IsOngoing }
        currentEvaluateShotUseCase().resetCurrentTurn()
        currentTrackUseCaseUseCase().resetCurrentTurn()
    }

    fun changeTurn() {
        _turnState.update { TurnState.IsOngoing }
        currentEvaluateShotUseCase().currentShotResult?.let { result ->
            if (result.isGameOver()) {
                _isGameFinished.update { true }
            } else {
                nextTurn()
            }
        }
    }

    fun eraseShot() {
        currentEvaluateShotUseCase().eraseShot()
        currentTrackUseCaseUseCase().eraseShot()
    }

    suspend fun finishGame() {
        val winner = if (players.count() == 1) null else currentPlayer.value
        val game = Game(
            players = players,
            winner = winner,
            gameGoal = goal,
            timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
        val gameHistory = GameHistory(
            game = game,
            trackUserHistoryUseCases.map { it.playerHistory.value }.sortedBy {
                game.players.indexOf(it.player)
            }
        )
        saveGameHistoryUseCase.execute(gameHistory = gameHistory)
    }

    private fun currentEvaluateShotUseCase(): EvaluateShotUseCase {
        return evaluateShotUseCases[players.indexOf(currentPlayer.value)]
    }

    private fun currentTrackUseCaseUseCase(): TrackUserHistoryUseCase {
        return trackUserHistoryUseCases[players.indexOf(currentPlayer.value)]
    }

    private fun nextTurn() {
        val index = players.indexOf(currentPlayer.value)
        val nextPlayer = players.getOrNull(index + 1) ?: kotlin.run {
            players[0]
        }
        _currentPlayer.update { nextPlayer }
    }
}