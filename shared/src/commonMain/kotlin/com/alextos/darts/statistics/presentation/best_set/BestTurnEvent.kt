package com.alextos.darts.statistics.presentation.best_set

import com.alextos.darts.core.domain.model.Turn

sealed class BestTurnEvent {
    data class ShowBestTurnOfPlayer(val turn: Turn): BestTurnEvent()
}