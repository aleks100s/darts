package com.alextos.darts.game.data

import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.game.domain.models.Sector
import com.alextos.darts.game.domain.models.Set
import com.alextos.darts.game.domain.models.Shot
import com.alextos.darts.players.domain.models.Player
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