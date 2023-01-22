package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Set

data class StatisticsSet(
    val player: Player,
    val set: Set
)