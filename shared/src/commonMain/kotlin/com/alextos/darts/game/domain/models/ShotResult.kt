package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Shot

sealed class ShotResult(val shot: Shot, val leftAfter: Int) {
    class Overkill(shot: Shot, leftAfter: Int) : ShotResult(shot, leftAfter)
    class InvalidShot(shot: Shot, leftAfter: Int): ShotResult(shot, leftAfter)
    class Regular(shot: Shot, leftAfter: Int): ShotResult(shot, leftAfter)
    class Win(shot: Shot, leftAfter: Int): ShotResult(shot, leftAfter)

    fun isGameOver(): Boolean = leftAfter == 0
    fun isTerminatingTurn(): Boolean = when (this) {
        is Overkill -> true
        is InvalidShot -> true
        else -> false
    }
}
