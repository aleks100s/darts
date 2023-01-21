package com.alextos.darts.statistics.data

import com.alextos.darts.game.domain.models.Sector
import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.game.domain.models.Shot
import database.GetBestSet
import database.GetPlayerBestSet

fun List<GetBestSet>.bestSetToSet(): Set? {
    return groupBy { it.setId }
        .entries
        .map { (_, group) ->
            val shots = group.map { Shot(Sector.getSector(it.sectorId.toInt())) }
            Set(
                shots = shots,
                leftAfter = group.first().leftAfter.toInt(),
                isOverkill = group.first().isOverkill == 1L
            )
        }.firstOrNull()
}

fun List<GetPlayerBestSet>.playerBestSetToSet(): Set? {
    return groupBy { it.setId }
        .entries
        .map { (_, group) ->
            val shots = group.map { Shot(Sector.getSector(it.sectorId.toInt())) }
            Set(
                shots = shots,
                leftAfter = group.first().leftAfter.toInt(),
                isOverkill = group.first().isOverkill == 1L
            )
        }.firstOrNull()
}