package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player

data class GamePlayerResult(
    val player: Player,
    val score: Int,
    val turnAverage: Int,
    val overallTurnAverage: Int,
    val isCurrentPlayer: Boolean
)