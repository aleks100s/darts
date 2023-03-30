package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.Sector

data class SectorHeat(
    val sector: Sector,
    val count: Int,
    val heat: Float
)