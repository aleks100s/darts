package com.alextos.darts.game.data

import com.alextos.darts.game.domain.models.Player
import database.PlayerEntity

fun PlayerEntity.toPlayer(): Player {
    return Player(
        id = id,
        name = name
    )
}