package com.alextos.darts.game.presentation.game_list

import com.alextos.darts.game.domain.models.Game

sealed class GameListEvent {
    data class SelectGame(val game: Game): GameListEvent()
    data class ShowDeleteGameDialog(val game: Game): GameListEvent()
    object CreateGame: GameListEvent()
    object DeleteGame: GameListEvent()
    object HideDeleteGameDialog: GameListEvent()
}