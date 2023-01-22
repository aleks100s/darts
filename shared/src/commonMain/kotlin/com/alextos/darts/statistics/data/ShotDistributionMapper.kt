package com.alextos.darts.statistics.data

import com.alextos.darts.statistics.domain.models.ShotDistribution
import database.GetShotDistribution

fun GetShotDistribution.toShotDistribution(): ShotDistribution {
    return ShotDistribution(
        missesCount = missesCount.toInt(),
        doubleBullseyeCount = doubleBullseyeCount.toInt(),
        bullseyeCount = singleBullseyeCount.toInt(),
        singlesCount = singlesCount.toInt(),
        doublesCount = doublesCount.toInt(),
        triplesCount = triplesCount.toInt()
    )
}