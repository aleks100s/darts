package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Shot

sealed class ShotResult(val shot: Shot, val leftAfter: Int) {
    class Overkill(shot: Shot, leftAfter: Int) : ShotResult(shot, leftAfter)
    class IncorrectShot(shot: Shot, leftAfter: Int): ShotResult(shot, leftAfter)
    class Regular(shot: Shot, leftAfter: Int): ShotResult(shot, leftAfter)

    fun isGameOver(): Boolean = leftAfter == 0
}
