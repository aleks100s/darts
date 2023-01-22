package com.alextos.darts.statistics.data

import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Shot

fun List<Long>.sectorsToShots(): List<Shot> {
    return map { Shot(Sector.getSector(it.toInt())) }
}

fun List<Long>.playerSectorsToShots(): List<Shot> {
    return map { Shot(Sector.getSector(it.toInt())) }
}