package com.alextos.darts.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Shot(
    val sector: Sector
)
