package com.alextos.darts.game.data

import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.core.domain.model.Shot
import com.alextos.darts.core.domain.model.Player
import database.GetPlayerHistory

fun List<GetPlayerHistory>.toPlayerHistory(player: Player): PlayerHistory {
    val sets = mutableListOf<Turn>()
        this.groupBy { it.setId }
        .entries
        .map { (_, group) ->
            val shots = group.map { Shot(Sector.getSector(it.sectorId.toInt())) }
            val set = Turn(
                shots = shots,
                leftAfter = group.first().leftAfter.toInt(),
                isOverkill = group.first().isOverkill == 1L
            )
            sets.add(set)
        }
    return PlayerHistory(player, sets)
}