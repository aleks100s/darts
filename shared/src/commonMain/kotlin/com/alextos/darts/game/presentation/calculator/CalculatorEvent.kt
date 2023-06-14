package com.alextos.darts.game.presentation.calculator

import com.alextos.darts.core.domain.model.Sector

sealed class CalculatorEvent {
    data class MakeShot(val sector: Sector): CalculatorEvent()
    object BackButtonPressed: CalculatorEvent()
}