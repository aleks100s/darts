package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.model.Player

data class AveragePlayerValue(
    val player: Player,
    val setScore: Double,
    val shotValue: Double
)