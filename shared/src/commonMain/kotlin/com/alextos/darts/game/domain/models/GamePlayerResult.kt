package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player

data class GamePlayerResult(
    val player: Player,
    val score: Int,
    val average: Int,
    val isCurrentPlayer: Boolean
)