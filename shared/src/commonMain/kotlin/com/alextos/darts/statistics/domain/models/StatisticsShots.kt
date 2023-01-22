package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Shot

data class StatisticsShots(
    val player: Player,
    val shots: List<Shot>
)