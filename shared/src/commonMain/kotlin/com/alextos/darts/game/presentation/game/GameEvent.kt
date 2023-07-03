package com.alextos.darts.game.presentation.game

import com.alextos.darts.core.domain.model.Sector

sealed class GameEvent {
    data class MakeShot(val sector: Sector): GameEvent()
    object ReturnToGame: GameEvent()
    object ResetCurrentTurn: GameEvent()
    object ChangeTurn: GameEvent()
    object EraseHit: GameEvent()
    object BackButtonPressed: GameEvent()
    object SaveGameProgress: GameEvent()
}