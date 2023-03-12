package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.Player

data class PlayerGameValue(
    val player: Player,
    val value: Int
)