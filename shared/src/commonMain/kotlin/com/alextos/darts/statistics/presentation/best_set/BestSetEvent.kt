package com.alextos.darts.statistics.presentation.best_set

import com.alextos.darts.core.domain.Set

sealed class BestSetEvent {
    data class ShowBestSetOfPlayer(val set: Set): BestSetEvent()
}