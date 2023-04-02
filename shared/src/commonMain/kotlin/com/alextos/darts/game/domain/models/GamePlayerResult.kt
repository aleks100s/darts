package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player

data class GamePlayerResult(
    val player: Player,
    val result: Int,
    val isCurrentPlayer: Boolean
)