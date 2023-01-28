package com.alextos.darts.game.presentation.history

import com.alextos.darts.game.domain.models.PlayerHistory

data class HistoryState(
    val gameHistory: List<PlayerHistory> = listOf(),
    val isRecapVisible: Boolean = false
)
