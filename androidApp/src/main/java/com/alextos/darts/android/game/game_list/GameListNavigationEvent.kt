package com.alextos.darts.android.game.game_list

import com.alextos.darts.game.domain.models.Game

sealed class GameListNavigationEvent {
    data class SelectGame(val game: Game): GameListNavigationEvent()
    data class ReplayGame(val game: Game): GameListNavigationEvent()
    object CreateGame: GameListNavigationEvent()
    object BackButtonPressed: GameListNavigationEvent()
    object ShowCalculator: GameListNavigationEvent()
}
