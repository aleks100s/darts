package com.alextos.darts.game.domain.models

import com.alextos.darts.players.domain.models.Player
import kotlinx.datetime.LocalDateTime

data class Game(
    val id: Long? = null,
    val players: List<Player>,
    var winner: Player? = null,
    val gameGoal: Int,
    val timestamp: LocalDateTime
)
