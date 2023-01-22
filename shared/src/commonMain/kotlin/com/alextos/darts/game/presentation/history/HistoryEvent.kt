package com.alextos.darts.game.presentation.history

import com.alextos.darts.core.domain.Set

sealed class HistoryEvent {
    data class ShowDarts(val turns: List<Set>, val currentSet: Set): HistoryEvent()
}
