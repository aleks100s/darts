package com.alextos.darts.game.domain.useCases

import com.alextos.darts.players.domain.models.Player
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.Shot
import kotlinx.coroutines.flow.*

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

    private val _currentPlayer = MutableStateFlow(players[0])
    val currentPlayer: StateFlow<Player> = _currentPlayer

    private val _isGameFinished = MutableStateFlow(false)
    val isGameFinished: StateFlow<Boolean> = _isGameFinished

    val gameHistory = combine(trackUserHistoryUseCases.map { it.playerHistory }) { array ->
        array.toList()
    }

    fun makeShot(shot: Shot) {
        val evaluateShotUseCase = currentEvaluateShotUseCase()
        val result = evaluateShotUseCase.checkShot(shot)
        val trackUserHistoryUseCase = currentTrackUseCaseUseCase()
        trackUserHistoryUseCase.trackShotResult(result)

    }

    private fun currentEvaluateShotUseCase(): EvaluateShotUseCase {
        return evaluateShotUseCases[players.indexOf(currentPlayer.value)]
    }

    private fun currentTrackUseCaseUseCase(): TrackUserHistoryUseCase {
        return trackUserHistoryUseCases[players.indexOf(currentPlayer.value)]
    }

    private fun nextTurn() {
        when (players.count()) {
            1 -> {
                _currentPlayer.update { players[0] }
            }
            2 -> {
                when (_currentPlayer.value) {
                    players[0] -> _currentPlayer.update { players[1] }
                    players[1] -> _currentPlayer.update { players[0] }
                }
            }
            3 -> {
                when (_currentPlayer.value) {
                    players[0] -> _currentPlayer.update { players[1] }
                    players[1] -> _currentPlayer.update { players[2] }
                    players[2] -> _currentPlayer.update { players[0] }
                }
            }
            4 -> {
                when (_currentPlayer.value) {
                    players[0] -> _currentPlayer.update { players[1] }
                    players[1] -> _currentPlayer.update { players[2] }
                    players[2] -> _currentPlayer.update { players[3] }
                    players[3] -> _currentPlayer.update { players[0] }
                }
            }
            else -> {}
        }
    }
}