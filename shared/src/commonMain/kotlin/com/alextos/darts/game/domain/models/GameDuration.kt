package com.alextos.darts.game.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class GameDuration(
    val minutes: Int,
    val seconds: Int
) {
    val isEmpty: Boolean
        get() = minutes == 0 && seconds == 0
}