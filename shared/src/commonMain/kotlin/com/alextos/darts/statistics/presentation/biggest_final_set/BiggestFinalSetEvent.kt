package com.alextos.darts.statistics.presentation.biggest_final_set

import com.alextos.darts.game.domain.models.Set

sealed class BiggestFinalSetEvent {
    data class ShowBiggestFinalSetOfAll(val set: Set): BiggestFinalSetEvent()
    data class ShowBiggestFinalSetOfPlayer(val set: Set): BiggestFinalSetEvent()
}
