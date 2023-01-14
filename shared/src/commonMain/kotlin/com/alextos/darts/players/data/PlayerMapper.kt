package com.alextos.darts.players.data

import com.alextos.darts.players.domain.models.Player
import database.PlayerEntity

fun PlayerEntity.toPlayer(): Player {
    return Player(
        id = id,
        name = name
    )
}