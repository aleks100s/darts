package com.alextos.darts.game.domain.game_manager

import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.game.domain.models.ShotResult

class CurrentSetManager(goal: Int, private val finishWithDoubles: Boolean) {
    var currentShotResult: ShotResult? = null

    private val currentSet = mutableListOf<Shot>()
    private var reminder: Int = goal

    fun resetCurrentTurn() {
        reminder += currentSet.fold(0) { acc, shot -> acc + shot.sector.value }
        currentSet.clear()
        currentShotResult = null
    }

    fun eraseShot() {
        if (currentSet.count() == 3 || currentSet.isEmpty()) {
            return
        }
        currentSet.removeLastOrNull()?.let {
            reminder += it.sector.value
            currentShotResult = null
        }
    }

    fun checkShot(shot: Shot): ShotResult {
        currentShotResult = null
        clearCurrentSetIfNeeded()
        val value = shot.sector.value
        val currentShotResult = if (value > reminder) {
            restoreState()
            ShotResult.Overkill(shot, reminder)
        } else if (value == reminder) {
            if (finishWithDoubles) {
                if (shot.sector.isDouble() || shot.sector.isDoubleBullseye()) {
                    countShot(shot)
                } else {
                    restoreState()
                    ShotResult.IncorrectShot(shot, reminder)
                }
            } else {
                countShot(shot)
            }
        }  else if (finishWithDoubles && reminder - value == 1) {
            restoreState()
            ShotResult.IncorrectShot(shot, reminder)
        } else {
            countShot(shot)
        }
        this.currentShotResult = currentShotResult
        return currentShotResult
    }

    private fun clearCurrentSetIfNeeded() {
        if (currentSet.count() == 3) {
            currentSet.clear()
        }
    }

    private fun countShot(shot: Shot): ShotResult {
        reminder -= shot.sector.value
        currentSet.add(shot)
        return ShotResult.Regular(shot, reminder)
    }

    private fun restoreState() {
        reminder += currentSet.fold(0) { acc, s ->
            acc + s.sector.value
        }
        currentSet.clear()
    }
}