package com.alextos.darts.game.presentation.game

import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Turn

sealed class GameEvent {
    data class MakeShot(val sector: Sector): GameEvent()
    data class ShowDarts(val turns: List<Turn>, val currentSet: Turn): GameEvent()
    object BackButtonPressed: GameEvent()
    object CloseGame: GameEvent()
    object ReturnToGame: GameEvent()
    object ShowGameInput: GameEvent()
    object HideGameInput: GameEvent()
    object ResetCurrentTurn: GameEvent()
    object ChangeTurn: GameEvent()
    object EraseHit: GameEvent()
    object FinishGame: GameEvent()
}