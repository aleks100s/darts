package com.alextos.darts.game.presentation.game

import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Turn

sealed class GameEvent {
    data class MakeShot(val sector: Sector): GameEvent()
    data class ShowDarts(val turns: List<Turn>, val currentPage: Int): GameEvent()
    data class ShowHistory(val page: Int): GameEvent()
    object BackButtonPressed: GameEvent()
    object CloseGame: GameEvent()
    object ReturnToGame: GameEvent()
    object ResetCurrentTurn: GameEvent()
    object ChangeTurn: GameEvent()
    object EraseHit: GameEvent()
    object ReplayGame: GameEvent()
    object ShowGameSettings: GameEvent()
}