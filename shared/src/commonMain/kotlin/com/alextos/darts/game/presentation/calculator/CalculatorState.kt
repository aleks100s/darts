package com.alextos.darts.game.presentation.calculator

import com.alextos.darts.game.domain.models.PlayerHistory

data class CalculatorState(
    val history: PlayerHistory? = null
)