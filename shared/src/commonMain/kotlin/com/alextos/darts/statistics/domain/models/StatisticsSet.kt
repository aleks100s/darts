package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Set

data class StatisticsSet(
    val player: Player,
    val set: Set
)