package com.alextos.darts.game.domain.models

import com.alextos.darts.players.domain.models.Player

data class PlayerHistory(
    val player: Player,
    val turns: List<Set> = listOf()
)
