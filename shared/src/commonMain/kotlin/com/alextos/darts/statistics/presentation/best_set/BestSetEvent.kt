package com.alextos.darts.statistics.presentation.best_set

import com.alextos.darts.core.domain.Set

sealed class BestSetEvent {
    data class ShowBestSetOfAll(val set: Set): BestSetEvent()
    data class ShowBestSetOfPlayer(val set: Set): BestSetEvent()
}