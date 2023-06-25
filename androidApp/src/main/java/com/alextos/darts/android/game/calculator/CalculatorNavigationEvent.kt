package com.alextos.darts.android.game.calculator

import com.alextos.darts.core.domain.model.Turn

sealed class CalculatorNavigationEvent {
    data class ShowHistory(val turns: List<Turn>): CalculatorNavigationEvent()
    object BackButtonPressed: CalculatorNavigationEvent()
}
