package com.alextos.darts.game.data

import com.alextos.darts.core.domain.model.Player
import database.PlayerEntity

fun PlayerEntity.toPlayer(): Player {
    return Player(
        id = id,
        name = name
    )
}