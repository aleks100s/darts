package com.alextos.darts.game.data

import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Set
import com.alextos.darts.core.domain.Shot
import com.alextos.darts.core.domain.Player
import database.GetPlayerHistory

fun List<GetPlayerHistory>.toPlayerHistory(player: Player): PlayerHistory {
    val sets = mutableListOf<Set>()
        this.groupBy { it.setId }
        .entries
        .map { (_, group) ->
            val shots = group.map { Shot(Sector.getSector(it.sectorId.toInt())) }
            val set = Set(
                shots = shots,
                leftAfter = group.first().leftAfter.toInt(),
                isOverkill = group.first().isOverkill == 1L
            )
            sets.add(set)
        }
    return PlayerHistory(player, sets)
}