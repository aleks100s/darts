package com.alextos.darts.game.presentation.game_list

import com.alextos.darts.game.domain.models.Game

sealed class GameListEvent {
    data class ShowDeleteGameDialog(val game: Game): GameListEvent()
    data class ShowActionsDialog(val game: Game): GameListEvent()
    object DeleteGame: GameListEvent()
    object HideDeleteGameDialog: GameListEvent()
    object HideActionsDialog: GameListEvent()
    object ReplayGame: GameListEvent()
    object ResumeGame: GameListEvent()
}