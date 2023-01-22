package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Set

data class PlayerHistory(
    val player: Player,
    val turns: List<Set> = listOf()
)
