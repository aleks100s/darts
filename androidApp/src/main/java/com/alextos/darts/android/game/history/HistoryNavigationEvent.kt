package com.alextos.darts.android.game.history

import com.alextos.darts.core.domain.model.Turn

sealed class HistoryNavigationEvent {
    data class ShowDarts(val turns: List<Turn>, val currentPage: Int): HistoryNavigationEvent()
    object ShowRecap: HistoryNavigationEvent()
}
