package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.models.*
import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Set
import com.alextos.darts.core.domain.Shot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TrackUserHistoryUseCase(player: Player) {
    private val _playerHistory = MutableStateFlow(PlayerHistory(player))
    val playerHistory: StateFlow<PlayerHistory> = _playerHistory

    fun resetCurrentTurn() {
        _playerHistory.update { playerHistory ->
            val turns = playerHistory.turns.toMutableList()
            turns.removeLast()
            playerHistory.copy(turns = turns)
        }
    }

    fun currentTurnResult(): Int {
        return _playerHistory.value.turns.lastOrNull()?.score() ?: 0
    }

    fun trackShotResult(shotResult: ShotResult): Boolean {
        return when (shotResult) {
            is ShotResult.Overkill -> {
                handle(shotResult, isOverkill = true)
            }
            is ShotResult.Regular -> {
                handle(shotResult, isOverkill = false)
            }
        }
    }

    private fun handle(
        shotResult: ShotResult,
        isOverkill: Boolean
    ): Boolean {
        var nextTurn = false
        _playerHistory.update { history ->
            val turns = history.turns.toMutableList()
            turns.removeLastOrNull()?.let { set ->
                nextTurn = if (set.shots.count() == 3) {
                    turns.add(set)
                    val newSet = createNewSet(shotResult, isOverkill)
                    turns.add(newSet)
                    isOverkill || shotResult.isGameOver()
                } else {
                    val newShots = set.shots.toMutableList()
                    newShots.add(shotResult.shot)
                    if (isOverkill && newShots.count() == 2) {
                        newShots.add(Shot(Sector.None))
                    }
                    val newSet = set.copy(
                        shots = newShots,
                        leftAfter = shotResult.leftAfter,
                        isOverkill = isOverkill
                    )
                    turns.add(newSet)
                    newShots.count() == 3
                            || isOverkill && newShots.count() == 2
                            || shotResult.isGameOver()
                }

            } ?: run {
                val newSet = createNewSet(shotResult, isOverkill)
                turns.add(newSet)
                nextTurn = isOverkill || shotResult.isGameOver()
            }
            history.copy(turns = turns)
        }
        return nextTurn
    }

    private fun createNewSet(
        shotResult: ShotResult,
        isOverkill: Boolean
    ): Set {
        return Set(
            shots = if (isOverkill) {
                listOf(
                    shotResult.shot,
                    Shot(Sector.None),
                    Shot(Sector.None)
                )
            } else {
                listOf(shotResult.shot)
                   },
            isOverkill = isOverkill,
            leftAfter = shotResult.leftAfter
        )
    }
}