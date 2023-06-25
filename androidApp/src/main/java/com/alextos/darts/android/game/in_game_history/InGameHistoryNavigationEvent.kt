package com.alextos.darts.android.game.in_game_history

import com.alextos.darts.core.domain.model.Turn

sealed class InGameHistoryNavigationEvent {
    data class SelectTurn(val list: List<Turn>, val index: Int): InGameHistoryNavigationEvent()
    object BackButtonPressed: InGameHistoryNavigationEvent()
}
