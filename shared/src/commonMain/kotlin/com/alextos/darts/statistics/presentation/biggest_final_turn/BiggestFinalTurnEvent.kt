package com.alextos.darts.statistics.presentation.biggest_final_turn

import com.alextos.darts.core.domain.model.Turn

sealed class BiggestFinalTurnEvent {
    data class ShowBiggestFinalTurnOfPlayer(val turn: Turn): BiggestFinalTurnEvent()
}
