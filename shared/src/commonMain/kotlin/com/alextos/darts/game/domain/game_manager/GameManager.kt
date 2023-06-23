package com.alextos.darts.game.domain.game_manager

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.game.domain.models.GameSettings
import com.alextos.darts.game.domain.useCases.SaveGameHistoryUseCase
import com.alextos.darts.game.presentation.game.TurnState
import com.alextos.darts.game.domain.useCases.GetPlayerAverageTurnUseCase
import com.alextos.darts.game.presentation.game.GameResult
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GameManager(
    private val saveGameHistoryUseCase: SaveGameHistoryUseCase,
    getPlayerAverageTurnUseCase: GetPlayerAverageTurnUseCase,
    gameSettings: GameSettings?
) {
    companion object {
        private const val TURNS_LIMIT = 20
    }

    private val goal = gameSettings?.selectedGameGoal ?: 0
    private val finishWithDoubles = gameSettings?.isFinishWithDoublesChecked ?: false
    private val turnsLimitEnabled = gameSettings?.isTurnLimitEnabled ?: true
    private val isRandomPlayerOrderEnabled = gameSettings?.isRandomPlayersOrderChecked ?: false
    private val isStatisticsEnabled = gameSettings?.isStatisticsEnabled ?: true

    private val startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    private val players = if (isRandomPlayerOrderEnabled) {
        gameSettings?.selectedPlayers?.shuffled() ?: listOf()
    } else {
        gameSettings?.selectedPlayers ?: listOf()
    }

    private val playerHistoryManagers by lazy {
        players.map {
            PlayerHistoryManager(it, goal, finishWithDoubles)
        }
    }

    private val _turnState: MutableStateFlow<TurnState> = MutableStateFlow(TurnState.IsOngoing)
    val turnState: StateFlow<TurnState> = _turnState

    private val _currentPlayer = MutableStateFlow(players[0])
    val currentPlayer: StateFlow<Player> = _currentPlayer

    private val _gameResult = MutableStateFlow<GameResult?>(null)
    val gameResult: StateFlow<GameResult?> = _gameResult

    val gameHistory = combine(playerHistoryManagers.map { it.playerHistory }) { array ->
        array.toList()
    }

    val averageTurns = combine(
        players.map { getPlayerAverageTurnUseCase.execute(it) }
    ) { it.toList() }

    fun getGoal(): Int = goal

    fun makeShot(shot: Shot) {
        if (_gameResult.value != null || turnState.value.isInputDisabled()) {
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
            val winner = if (players.count() == 1) null else currentPlayer.value
            finishGame(winner)
        } else if (turnsLimitEnabled && isTurnLimitReached()) {
            terminateGame()
        } else {
            nextTurn()
        }
    }

    fun eraseShot() {
        currentPlayerHistoryManager().undoLastShot()
    }

    private fun currentPlayerHistoryManager(): PlayerHistoryManager {
        return playerHistoryManagers[players.indexOf(currentPlayer.value)]
    }

    private fun isTurnLimitReached(): Boolean {
        return playerHistoryManagers
            .map { it.playerHistory.value }
            .all { it.turnsNumber == TURNS_LIMIT }
    }

    private suspend fun finishGame(winner: Player?) {
        val game = Game(
            players = players,
            winner = winner,
            gameGoal = goal,
            finishTimestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            startTimestamp = startTime,
            isWinWithDoublesEnabled = finishWithDoubles,
            isRandomPlayerOrderEnabled = isRandomPlayerOrderEnabled,
            isStatisticsEnabled = isStatisticsEnabled,
            isTurnLimitEnabled = turnsLimitEnabled
        )
        val gameHistory = GameHistory(
            game = game,
            playerHistoryManagers.map { it.playerHistory.value }.sortedBy {
                game.players.indexOf(it.player)
            }
        )
        saveGameHistoryUseCase.execute(gameHistory = gameHistory)
        _gameResult.update {
            if (players.count() == 1) {
                GameResult.TrainingFinished
            } else {
                if (winner == null) {
                    GameResult.Draw
                } else {
                    GameResult.Winner(winner.name)
                }
            }
        }
    }

    private suspend fun terminateGame() {
        val playerHistories = playerHistoryManagers.map { it.playerHistory.value }
        val playerHistoryWithHighestScore = playerHistories.maxBy { it.score }
        val highestScoreCount = playerHistories.count { it.score == playerHistoryWithHighestScore.score }
        if (highestScoreCount > 1) {
            finishGame(winner = null)
        } else {
            finishGame(playerHistoryWithHighestScore.player)
        }
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