package com.alextos.darts.game.presentation.history

import com.alextos.darts.game.domain.models.Set

sealed class HistoryEvent {
    data class ShowDarts(val turns: List<Set>, val currentSet: Set): HistoryEvent()
}
