package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.models.Shot
import com.alextos.darts.game.domain.models.ShotResult

class EvaluateShotUseCase(goal: Int) {
    private val currentSet = mutableListOf<Shot>()
    private var reminder: Int = goal

    fun checkShot(shot: Shot): ShotResult {
        val value = shot.sector.value
        return if (value > reminder) {
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
            if (currentSet.count() == 3) {
                currentSet.clear()
            }
            ShotResult.Regular(shot, reminder)
        }
    }
}