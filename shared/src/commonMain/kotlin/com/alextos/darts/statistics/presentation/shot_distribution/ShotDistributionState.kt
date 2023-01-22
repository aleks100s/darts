package com.alextos.darts.statistics.presentation.shot_distribution

import com.alextos.darts.statistics.domain.models.PlayerShotDistribution

data class ShotDistributionState(
    val playersDistribution: List<PlayerShotDistribution> = listOf()
)