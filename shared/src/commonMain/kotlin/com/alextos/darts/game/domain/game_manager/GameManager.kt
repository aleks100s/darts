package com.alextos.darts.game.domain.game_manager

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.game.domain.TurnLimitReachedException
import com.alextos.darts.game.domain.models.GameSettings
import com.alextos.darts.game.domain.models.PlayerGameValue
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.game.domain.useCases.RestoreGameHistoryUseCase
import com.alextos.darts.game.domain.useCases.SaveGameHistoryUseCase
import com.alextos.darts.game.presentation.game.TurnState
import com.alextos.darts.game.domain.useCases.GetPlayerAverageTurnUseCase
import com.alextos.darts.game.presentation.game.GameResult
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class GameManager(
    private val saveGameHistoryUseCase: SaveGameHistoryUseCase,
    private val restoreGameHistoryUseCase: RestoreGameHistoryUseCase,
    getPlayerAverageTurnUseCase: GetPlayerAverageTurnUseCase,
    gameSettings: GameSettings?
) {
    companion object {
        private const val TURNS_LIMIT = 20
    }

    val goal = gameSettings?.selectedGameGoal ?: 0

    private val finishWithDoubles = gameSettings?.isFinishWithDoublesChecked ?: false
    private val turnsLimitEnabled = gameSettings?.isTurnLimitEnabled ?: true
    private val isRandomPlayerOrderEnabled = gameSettings?.isRandomPlayersOrderChecked ?: false
    private val isStatisticsEnabled = gameSettings?.isStatisticsEnabled ?: true
    private var startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    private val players: List<Player>
    private val playerHistoryManagers: MutableStateFlow<List<PlayerHistoryManager>>

    private val _turnState: MutableStateFlow<TurnState> = MutableStateFlow(TurnState.IsOngoing)
    val turnState: StateFlow<TurnState> = _turnState

    private val _currentPlayer: MutableStateFlow<Player>
    val currentPlayer: StateFlow<Player>

    private val _gameResult = MutableStateFlow<GameResult?>(null)
    val gameResult: StateFlow<GameResult?> = _gameResult

    val gameHistory: Flow<List<PlayerHistory>>
    val averageTurns: Flow<List<PlayerGameValue>>

    var currentTurn = 1

    init {
        players = if (isRandomPlayerOrderEnabled && gameSettings?.isResumed != true) {
            gameSettings?.selectedPlayers?.shuffled() ?: listOf()
        } else {
            gameSettings?.selectedPlayers ?: listOf()
        }

        _currentPlayer = MutableStateFlow(players[0])
        currentPlayer = _currentPlayer

        playerHistoryManagers = MutableStateFlow(
            players.map { player ->
                PlayerHistoryManager(player, goal, finishWithDoubles)
            }
        )
        gameHistory = combine(playerHistoryManagers.value.map { it.playerHistory }) { array ->
            array.toList()
        }

        averageTurns = combine(
            players.map { getPlayerAverageTurnUseCase.execute(it) }
        ) { it.toList() }
    }

    suspend fun restorePausedGame(game: Game) {
        restoreGameHistoryUseCase.execute(game, game.players)
            .map { playerHistory ->
                playerHistoryManagers.value.firstOrNull { playerHistoryManager ->
                    playerHistoryManager.player == playerHistory.player
                }?.setHistory(playerHistory)
            }
        restoreCurrentPlayer()
        restoreCurrentTurnNumber()
        restoreTime(game)
    }

    private fun restoreCurrentPlayer() {
        val playerHistories = playerHistoryManagers.value.map { it.playerHistory.value }
        val playerWithOngoingTurn = playerHistories.minByOrNull { it.turns.count() }
            ?: playerHistories.firstOrNull { it.turns.lastOrNull()?.shots?.count() != Turn.turnLimit }
        if (playerWithOngoingTurn != null) {
            _currentPlayer.update { playerWithOngoingTurn.player }
        }
    }

    private fun restoreCurrentTurnNumber() {
        val turns = currentPlayerHistoryManager().playerHistory.value.turns
        currentTurn = turns.count()
        if (currentTurn == 0) {
            currentTurn += 1
        }
        if (turns.lastOrNull()?.shots?.count() == 3) {
            currentTurn += 1
        }
    }

    private fun restoreTime(game: Game) {
        if (game.startTimestamp == null) {
            return
        }
        val timeZone = TimeZone.currentSystemDefault()
        val finishInstant = game.finishTimestamp.toInstant(timeZone)
        val startInstant = game.startTimestamp.toInstant(timeZone)
        val gap = finishInstant.minus(startInstant)
        startTime = startTime.toInstant(timeZone).minus(gap).toLocalDateTime(timeZone)
    }

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
            finishGame(winner, isOngoing = false)
        } else {
            try {
                nextTurn()
            } catch (e: TurnLimitReachedException) {
                terminateGame(isOngoing = false)
            }
        }
    }

    fun eraseShot() {
        currentPlayerHistoryManager().undoLastShot()
    }

    suspend fun saveGameProgress() {
        terminateGame(isOngoing = true)
    }

    private fun currentPlayerHistoryManager(): PlayerHistoryManager {
        return playerHistoryManagers.value[players.indexOf(currentPlayer.value)]
    }

    private fun nextTurn() {
        currentPlayerHistoryManager().finishTurn()
        val nextPlayer = getNextPlayer()
        if (nextPlayer == null) {
            restartPlayerOrder()
        } else {
            _currentPlayer.update { nextPlayer }
        }
    }

    private fun getNextPlayer(): Player? {
        val index = players.indexOf(currentPlayer.value)
        return players.getOrNull(index + 1)
    }

    private fun restartPlayerOrder() {
        if (turnsLimitEnabled && currentTurn == TURNS_LIMIT) {
            throw TurnLimitReachedException()
        } else {
            _currentPlayer.update { players[0] }
        }
        currentTurn += 1
    }

    private suspend fun terminateGame(isOngoing: Boolean) {
        if (isOngoing) {
            finishGame(winner = null, isOngoing = isOngoing)
            return
        }
        val playerHistories = playerHistoryManagers.value.map { it.playerHistory.value }
        val playerHistoryWithHighestScore = playerHistories.minBy { it.leftAfter }
        val highestScoreCount = playerHistories.count { it.leftAfter == playerHistoryWithHighestScore.leftAfter }
        if (highestScoreCount > 1 || playerHistories.count() == 1) {
            finishGame(winner = null, isOngoing = false)
        } else {
            finishGame(playerHistoryWithHighestScore.player, isOngoing = false)
        }
    }

    private suspend fun finishGame(winner: Player?, isOngoing: Boolean) {
        val game = Game(
            players = players,
            winner = winner,
            gameGoal = goal,
            finishTimestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            startTimestamp = startTime,
            isWinWithDoublesEnabled = finishWithDoubles,
            isRandomPlayerOrderEnabled = isRandomPlayerOrderEnabled,
            isStatisticsEnabled = isStatisticsEnabled,
            isTurnLimitEnabled = turnsLimitEnabled,
            isPaused = isOngoing
        )
        val gameHistory = GameHistory(
            game = game,
            playerHistoryManagers.value.map { it.playerHistory.value }.sortedBy {
                game.players.indexOf(it.player)
            }
        )
        saveGameHistoryUseCase.execute(gameHistory = gameHistory)
        _gameResult.update {
            if (isOngoing) {
                GameResult.GamePaused
            } else {
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
    }
}