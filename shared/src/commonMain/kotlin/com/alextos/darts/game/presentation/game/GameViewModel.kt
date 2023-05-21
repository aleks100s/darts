package com.alextos.darts.game.presentation.game

import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.util.toCommonStateFlow
import com.alextos.darts.game.domain.game_manager.GameManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameManager: GameManager,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(GameState())

    val state = combine(
        combine(_state, gameManager.gameHistory) { state, history -> state to history },
        combine(gameManager.currentPlayer, gameManager.isGameFinished) { currentPlayer, isGameFinished -> currentPlayer to isGameFinished },
        combine(gameManager.turnState, gameManager.averageTurns) { turnState, averageTurns -> turnState to averageTurns },
    ) { stateAndHistory, playerAndIsGameFinished, turnStateAndAverageTurns ->
        stateAndHistory.first.copy(
            gameHistory = stateAndHistory.second,
            currentPlayer = playerAndIsGameFinished.first,
            isGameFinished = playerAndIsGameFinished.second,
            turnState = turnStateAndAverageTurns.first,
            gameGoal = gameManager.getGoal(),
            averageTurns = turnStateAndAverageTurns.second
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