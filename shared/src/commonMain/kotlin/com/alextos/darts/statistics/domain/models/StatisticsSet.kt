package com.alextos.darts.statistics.domain.models

import com.alextos.darts.game.domain.models.Player
import com.alextos.darts.game.domain.models.Set

data class StatisticsSet(
    val player: Player,
    val set: Set
)