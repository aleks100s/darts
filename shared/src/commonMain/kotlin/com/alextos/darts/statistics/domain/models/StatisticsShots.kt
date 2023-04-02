package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Shot

data class StatisticsShots(
    val player: Player,
    val shots: List<Shot>
)