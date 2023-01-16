package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.models.Player
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.game.domain.models.Shot
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GameManager(
    private val saveGameHistoryUseCase: SaveGameHistoryUseCase,
    private val players: List<Player>,
    private val goal: Int
) {
    private val evaluateShotUseCases by lazy {
        players.map {
            EvaluateShotUseCase(goal)
        }
    }

    private val trackUserHistoryUseCases by lazy {
        players.map {
            TrackUserHistoryUseCase(it)
        }
    }

    private val _isTurnChangingDialogNeeded = MutableStateFlow(false)
    val isTurnChangingDialogNeeded: StateFlow<Boolean> = _isTurnChangingDialogNeeded

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
            if (result.isGameOver()) {
                finishGame()
            } else {
                nextTurn()
            }
        }
    }

    fun turnChanged() {
        _isTurnChangingDialogNeeded.update { false }
    }

    private fun currentEvaluateShotUseCase(): EvaluateShotUseCase {
        return evaluateShotUseCases[players.indexOf(currentPlayer.value)]
    }

    private fun currentTrackUseCaseUseCase(): TrackUserHistoryUseCase {
        return trackUserHistoryUseCases[players.indexOf(currentPlayer.value)]
    }

    private fun finishGame() {
        _isGameFinished.update { true }
        val game = Game(
            players = players,
            winner = currentPlayer.value,
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

    private fun nextTurn() {
        when (currentPlayer.value) {
            players.getOrNull(0) -> _currentPlayer.update { players.getOrNull(1) ?: it }
            players.getOrNull(1) -> _currentPlayer.update { players.getOrNull(2) ?: players[0] }
            players.getOrNull(2) -> _currentPlayer.update { players.getOrNull(3) ?: players[0] }
            players.getOrNull(3) -> _currentPlayer.update { players.getOrNull(0) ?: it }
            else -> { _currentPlayer.update { it }}
        }
        _isTurnChangingDialogNeeded.update { true }
    }
}