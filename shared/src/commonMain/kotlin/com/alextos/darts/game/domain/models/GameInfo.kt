package com.alextos.darts.game.domain.models

import com.alextos.darts.players.domain.models.Player

data class GameInfo(
    val players: List<Player>,
    val gameGoal: Int
)
