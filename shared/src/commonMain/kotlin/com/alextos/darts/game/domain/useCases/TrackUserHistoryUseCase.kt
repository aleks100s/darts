package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.game.domain.models.ShotResult
import com.alextos.darts.players.domain.models.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TrackUserHistoryUseCase(private val player: Player) {
    private val _playerHistory = MutableStateFlow(PlayerHistory(player))
    val playerHistory: Flow<PlayerHistory> = _playerHistory

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
                    val newSet = createNewSet(shotResult, isOverkill)
                    turns.add(newSet)
                    isOverkill
                } else {
                    val newShots = set.shots.toMutableList()
                    newShots.add(shotResult.shot)
                    val newSet = set.copy(
                        shots = newShots,
                        leftAfter = shotResult.leftAfter
                    )
                    turns.add(newSet)
                    newShots.count() == 3
                }
            } ?: run {
                val newSet = createNewSet(shotResult, isOverkill)
                turns.add(newSet)
                nextTurn = isOverkill
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
            shots = listOf(shotResult.shot),
            isOverkill = isOverkill,
            leftAfter = shotResult.leftAfter
        )
    }
}