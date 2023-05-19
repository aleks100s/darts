package com.alextos.darts.statistics.presentation.shot_distribution

import com.alextos.darts.statistics.domain.models.PlayerShotDistribution

data class ShotDistributionState(
    val distribution: PlayerShotDistribution? = null,
    val isLoading: Boolean = true
)