package com.alextos.darts.statistics.domain.models

import com.alextos.darts.game.domain.models.Player
import com.alextos.darts.game.domain.models.Shot

data class StatisticsShots(
    val player: Player,
    val shots: List<Shot>
)