package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerGameValue(
    val player: Player,
    val value: Int
)