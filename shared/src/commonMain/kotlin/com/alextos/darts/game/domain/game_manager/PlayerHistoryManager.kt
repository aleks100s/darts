package com.alextos.darts.game.domain.game_manager

import com.alextos.darts.game.domain.models.*
import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.util.extensions.fill
import com.alextos.darts.game.presentation.game.TurnState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PlayerHistoryManager(
    player: Player,
    goal: Int,
    private val finishWithDoubles: Boolean
) {
    private val currentTurnShots = mutableListOf<Shot>()
    private val _playerHistory = MutableStateFlow(PlayerHistory(player))
    val playerHistory: StateFlow<PlayerHistory> = _playerHistory

    private var reminder: Int = goal
    private var currentShotResult: ShotResult? = null

    fun makeShot(shot: Shot): TurnState {
        val result = checkShot(shot)
        return trackShotResult(result)
    }

    fun undoLastShot() {
        removeLastShotFromCurrentTurn()
        removeLastShotFromHistory()
    }

    fun resetCurrentTurn() {
        resetCurrentTurnState()
        deleteCurrentTurnFromHistory()
    }

    fun finishTurn() {
        if (currentTurnShots.count() == Turn.turnLimit) {
            currentTurnShots.clear()
        }
    }

    fun isGameOver(): Boolean {
        return currentShotResult?.isGameOver() ?: false
    }

    private fun checkShot(shot: Shot): ShotResult {
        val value = shot.sector.value
        val currentShotResult = if (value > reminder) {
            resetCurrentTurnState()
            ShotResult.Overkill(shot, reminder)
        } else if (value == reminder) {
            if (finishWithDoubles) {
                if (shot.sector.isDouble() || shot.sector.isDoubleBullseye()) {
                    countShot(shot)
                    ShotResult.Win(shot, reminder)
                } else {
                    resetCurrentTurnState()
                    ShotResult.InvalidShot(shot, reminder)
                }
            } else {
                countShot(shot)
                ShotResult.Win(shot, reminder)
            }
        }  else if (finishWithDoubles && reminder - value == 1) {
            resetCurrentTurnState()
            ShotResult.InvalidShot(shot, reminder)
        } else {
            countShot(shot)
            ShotResult.Regular(shot, reminder)
        }
        this.currentShotResult = currentShotResult
        return currentShotResult
    }

    private fun countShot(shot: Shot) {
        if (currentTurnShots.count() < 3) {
            reminder -= shot.sector.value
            currentTurnShots.add(shot)
        }
    }

    private fun trackShotResult(shotResult: ShotResult): TurnState {
        _playerHistory.update { history ->
            val turns = history.turns.toMutableList()
            val newTurn = if (turns.isEmpty() || turns.lastOrNull()?.shots?.count() == Turn.turnLimit) {
                Turn(
                    shots = prepareShots(shotResult),
                    isOverkill = shotResult.isTerminatingTurn(),
                    leftAfter = shotResult.leftAfter
                )
            } else {
                val currentTurn = turns.removeLast()
                val shots = prepareShots(shotResult, currentTurn.shots.toMutableList())
                currentTurn.copy(
                    shots = shots,
                    leftAfter = shotResult.leftAfter,
                    isOverkill = shotResult.isTerminatingTurn()
                )
            }
            turns.add(newTurn)
            history.copy(turns = turns)
        }
        val currentTurn = _playerHistory.value.turns.last()
        return if (currentTurn.shotsLeft() == 0) {
            val turnReminder = currentTurn.score()
            TurnState.IsOver(turnReminder)
        } else {
            TurnState.IsOngoing
        }
    }

    private fun prepareShots(
        shotResult: ShotResult,
        shots: MutableList<Shot> = mutableListOf()
    ): List<Shot> {
        shots.add(shotResult.shot)
        if (shotResult.isTerminatingTurn() || shotResult.isGameOver()) {
            shots.fill(Shot(Sector.None), toSize = 3)
        }
        return shots
    }

    private fun removeLastShotFromCurrentTurn() {
        if (currentTurnShots.count() == Turn.turnLimit || currentTurnShots.isEmpty()) {
            return
        }
        currentTurnShots.removeLastOrNull()?.let {
            reminder += it.sector.value
        }
    }

    private fun removeLastShotFromHistory() {
        val turns = _playerHistory.value.turns.toMutableList()
        turns.removeLastOrNull()?.let { lastTurn ->
            if (lastTurn.shots.count() == Turn.turnLimit || lastTurn.shots.isEmpty()) {
                return
            }
            val lastShots = lastTurn.shots.toMutableList()
            val lastShot = lastShots.removeLastOrNull()
            val set = Turn(
                shots = lastShots,
                isOverkill = false,
                leftAfter = lastTurn.leftAfter + (lastShot?.sector?.value ?: 0)
            )
            turns.add(set)
            _playerHistory.update { playerHistory ->
                playerHistory.copy(turns = turns)
            }
        }
    }

    private fun resetCurrentTurnState() {
        reminder += currentTurnShots.fold(0) { acc, s ->
            acc + s.sector.value
        }
        currentTurnShots.clear()
    }

    private fun deleteCurrentTurnFromHistory() {
        _playerHistory.update { playerHistory ->
            val turns = playerHistory.turns.toMutableList()
            turns.removeLast()
            playerHistory.copy(turns = turns)
        }
    }
}