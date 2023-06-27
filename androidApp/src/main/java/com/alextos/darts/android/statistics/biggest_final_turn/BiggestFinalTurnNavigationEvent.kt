package com.alextos.darts.android.statistics.biggest_final_turn

import com.alextos.darts.core.domain.model.Turn

sealed class BiggestFinalTurnNavigationEvent {
    data class ShowBiggestFinalTurnNavigationOfPlayer(val turn: Turn): BiggestFinalTurnNavigationEvent()
}
