package com.alextos.darts.game.presentation.game

import com.alextos.darts.game.domain.models.Sector

sealed class GameEvent {
    data class MakeShot(val sector: Sector): GameEvent()
    object BackButtonPressed: GameEvent()
    object CloseGame: GameEvent()
    object ReturnToGame: GameEvent()
}