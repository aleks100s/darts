package com.alextos.darts.statistics.data

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.models.PlayerShotDistribution
import com.alextos.darts.statistics.domain.models.ShotDistribution
import database.GetPlayerShotDistribution

fun GetPlayerShotDistribution.toPlayerShotDistribution(player: Player): PlayerShotDistribution {
    return PlayerShotDistribution(
        player = player,
        distribution = ShotDistribution(
            missesCount = missesCount.toInt(),
            doubleBullseyeCount = doubleBullseyeCount.toInt(),
            bullseyeCount = singleBullseyeCount.toInt(),
            singlesCount = singlesCount.toInt(),
            doublesCount = doublesCount.toInt(),
            triplesCount = triplesCount.toInt()
        )
    )
}