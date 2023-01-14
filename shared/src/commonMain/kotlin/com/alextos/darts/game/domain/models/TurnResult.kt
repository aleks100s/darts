package com.alextos.darts.game.domain.models

sealed class TurnResult {
    object Regular: TurnResult()
    object Overkill: TurnResult()
    object Win: TurnResult()
}
