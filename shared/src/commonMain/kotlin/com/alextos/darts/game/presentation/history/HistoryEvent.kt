package com.alextos.darts.game.presentation.history

import com.alextos.darts.core.domain.model.Turn

sealed class HistoryEvent {
    data class ShowDarts(val turns: List<Turn>, val currentPage: Int): HistoryEvent()
    object ShowRecap: HistoryEvent()
}
