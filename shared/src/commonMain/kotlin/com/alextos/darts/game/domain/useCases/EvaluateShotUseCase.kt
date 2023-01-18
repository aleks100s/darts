package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.models.Shot
import com.alextos.darts.game.domain.models.ShotResult

class EvaluateShotUseCase(goal: Int) {
    var currentShotResult: ShotResult? = null

    private val currentSet = mutableListOf<Shot>()
    private var reminder: Int = goal

    fun resetCurrentTurn() {
        reminder += currentSet.fold(0) { acc, shot -> acc + shot.sector.value }
        currentSet.clear()
        currentShotResult = null
    }

    fun checkShot(shot: Shot): ShotResult {
        currentShotResult = null
        if (currentSet.count() == 3) {
            currentSet.clear()
        }
        val value = shot.sector.value
        val currentShotResult = if (value > reminder) {
            reminder += currentSet.fold(0) { acc, s ->
                acc + s.sector.value
            }
            currentSet.clear()
            ShotResult.Overkill(shot, reminder)
        } else if (value == reminder) {
            ShotResult.Regular(shot, 0)
        } else {
            reminder -= value
            currentSet.add(shot)
            ShotResult.Regular(shot, reminder)
        }
        this.currentShotResult = currentShotResult
        return currentShotResult
    }
}