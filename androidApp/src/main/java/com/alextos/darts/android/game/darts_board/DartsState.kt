package com.alextos.darts.android.game.darts_board

import com.alextos.darts.core.domain.model.Turn
import kotlinx.serialization.Serializable

@Serializable
data class DartsState(
    val turns: List<Turn>,
    val currentPage: Int
)