package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.Player

data class PlayerShotDistribution(
    val player: Player,
    val distribution: ShotDistribution
)
