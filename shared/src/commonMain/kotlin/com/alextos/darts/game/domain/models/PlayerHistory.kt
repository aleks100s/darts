package com.alextos.darts.game.domain.models

data class PlayerHistory(
    val player: Player,
    val turns: List<Set> = listOf()
)
