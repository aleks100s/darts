package com.alextos.darts.android.statistics.best_turn

import com.alextos.darts.core.domain.model.Turn

sealed class BestTurnNavigationEvent {
    data class ShowBestTurnOfPlayer(val turn: Turn): BestTurnNavigationEvent()
}