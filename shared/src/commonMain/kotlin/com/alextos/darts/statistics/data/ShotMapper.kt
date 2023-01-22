package com.alextos.darts.statistics.data

import com.alextos.darts.game.domain.models.Sector
import com.alextos.darts.game.domain.models.Shot

fun List<Long>.sectorsToShots(): List<Shot> {
    return map { Shot(Sector.getSector(it.toInt())) }
}

fun List<Long>.playerSectorsToShots(): List<Shot> {
    return map { Shot(Sector.getSector(it.toInt())) }
}