package com.alextos.darts.statistics.data

import com.alextos.darts.core.domain.Player
import com.alextos.darts.statistics.domain.models.PlayerVictoryDistribution
import database.GetVictoryDistribution

fun GetVictoryDistribution.toPlayerVictoryDistribution(player: Player): PlayerVictoryDistribution {
    return PlayerVictoryDistribution(
        player = player,
        gamesCount = gamesCount.toInt(),
        victoriesCount = victoriesCount.toInt()
    )
}