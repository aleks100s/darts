package com.alextos.darts.game.presentation.game

import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Set

sealed class GameEvent {
    data class MakeShot(val sector: Sector): GameEvent()
    data class ShowDarts(val turns: List<Set>, val currentSet: Set): GameEvent()
    object BackButtonPressed: GameEvent()
    object CloseGame: GameEvent()
    object ReturnToGame: GameEvent()
    object ShowGameInput: GameEvent()
    object HideGameInput: GameEvent()
    object ResetCurrentTurn: GameEvent()
    object ChangeTurn: GameEvent()
}