package com.alextos.darts.statistics.presentation.biggest_final_set

import com.alextos.darts.core.domain.Set

sealed class BiggestFinalSetEvent {
    data class ShowBiggestFinalSetOfPlayer(val set: Set): BiggestFinalSetEvent()
}
