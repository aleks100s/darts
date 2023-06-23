package com.alextos.darts.game.presentation.game

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.game.domain.game_manager.GameManager
import com.alextos.darts.game.domain.models.GameSettings
import com.alextos.darts.game.domain.models.PlayerGameValue
import com.alextos.darts.game.domain.models.PlayerHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameManager: GameManager,
    gameSettings: GameSettings?,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(GameState())

    val state = combine(
        _state,
        gameManager.gameHistory,
        gameManager.currentPlayer,
        gameManager.gameResult,
        gameManager.turnState,
        gameManager.averageTurns
    ) { data ->
        val state = data[0] as GameState
        val gameHistory = (data[1] as List<*>).filterIsInstance<PlayerHistory>()
        val currentPlayer = data[2] as Player?
        val gameResult = data[3] as GameResult?
        val turnState = data[4] as TurnState
        val averageTurns = (data[5] as List<*>).filterIsInstance<PlayerGameValue>()
        state.copy(
            gameHistory = gameHistory,
            currentPlayer = currentPlayer,
            turnState = turnState,
            gameGoal = gameManager.getGoal(),
            averageTurns = averageTurns,
            isStatisticsEnabled = gameSettings?.isStatisticsEnabled == true,
            gameResult = gameResult
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GameState()
        )
        .toCommonStateFlow()

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.MakeShot -> {
                gameManager.makeShot(Shot(event.sector))
            }
            is GameEvent.BackButtonPressed -> {
                _state.update { it.copy(isCloseGameDialogOpened = true) }
            }
            is GameEvent.ReturnToGame -> {
                _state.update { it.copy(isCloseGameDialogOpened = false) }
            }
            is GameEvent.ResetCurrentTurn -> {
                gameManager.resetTurn()
            }
            is GameEvent.ChangeTurn -> {
                viewModelScope.launch(Dispatchers.Default) {
                    gameManager.changeTurn()
                }
            }
            is GameEvent.EraseHit -> {
                gameManager.eraseShot()
            }
            is GameEvent.CloseGame -> {}
            is GameEvent.ReplayGame -> {}
            is GameEvent.ShowDarts -> {}
            is GameEvent.ShowHistory -> {}
        }
    }
}