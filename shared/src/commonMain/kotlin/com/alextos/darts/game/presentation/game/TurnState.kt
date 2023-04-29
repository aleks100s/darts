package com.alextos.darts.game.presentation.game

import kotlinx.serialization.Serializable

@Serializable
sealed class TurnState {
    object IsOngoing: TurnState()
    data class IsOver(val result: Int): TurnState()
}