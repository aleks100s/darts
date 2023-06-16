package com.alextos.darts.game.presentation.calculator

import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Turn

sealed class CalculatorEvent {
    data class MakeShot(val sector: Sector): CalculatorEvent()
    data class ShowHistory(val turns: List<Turn>): CalculatorEvent()
    object UndoLastShot: CalculatorEvent()
    object BackButtonPressed: CalculatorEvent()
}