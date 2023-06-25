package com.alextos.darts.android.game.game

import com.alextos.darts.core.domain.model.Turn

sealed class GameNavigationEvent {
    data class ShowDarts(val turns: List<Turn>, val currentPage: Int): GameNavigationEvent()
    data class ShowHistory(val page: Int): GameNavigationEvent()
    object CloseGame: GameNavigationEvent()
    object ReplayGame: GameNavigationEvent()
    object ShowGameSettings: GameNavigationEvent()
}
