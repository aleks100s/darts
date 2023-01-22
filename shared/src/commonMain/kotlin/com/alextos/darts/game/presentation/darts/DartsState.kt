package com.alextos.darts.game.presentation.darts

import com.alextos.darts.core.domain.Shot

data class DartsState(
    val turns: List<List<Shot>> = listOf(),
    val currentPage: Int = 0
)