package com.alextos.darts.game.presentation.game

sealed class TurnState {
    object IsOngoing: TurnState()
    data class IsOver(val result: Int): TurnState()
}