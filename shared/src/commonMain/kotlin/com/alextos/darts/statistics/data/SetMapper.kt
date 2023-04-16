package com.alextos.darts.statistics.data

import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.core.domain.model.Shot
import database.GetPlayerBestSet
import database.GetPlayerBiggestFinalSet

fun List<GetPlayerBestSet>.playerBestSetToSet(): Turn? {
    return groupBy { it.setId }
        .entries
        .map { (_, group) ->
            val shots = group.map { Shot(Sector.getSector(it.sectorId.toInt())) }
            Turn(
                shots = shots,
                leftAfter = group.first().leftAfter.toInt(),
                isOverkill = group.first().isOverkill == 1L
            )
        }.firstOrNull()
}

fun List<GetPlayerBiggestFinalSet>.playerBiggestFinalSetToSet(): Turn? {
    return groupBy { it.setId }
        .entries
        .map { (_, group) ->
            val shots = group.map { Shot(Sector.getSector(it.sectorId.toInt())) }
            Turn(
                shots = shots,
                leftAfter = group.first().leftAfter.toInt(),
                isOverkill = group.first().isOverkill == 1L
            )
        }.firstOrNull()
}