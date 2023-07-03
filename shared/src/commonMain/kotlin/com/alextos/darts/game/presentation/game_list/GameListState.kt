package com.alextos.darts.game.presentation.game_list

import com.alextos.darts.game.domain.models.Game

data class GameListState(
    val isDeleteGameDialogShown: Boolean = false,
    val selectedGame: Game? = null,
    val isLoading: Boolean = true,
    val isActionsDialogShown: Boolean = false,
    val finishedGames: List<Game> = listOf(),
    val pausedGames: List<Game> = listOf()
) {
    val isEmpty: Boolean
        get() { return finishedGames.isEmpty() && pausedGames.isEmpty() }
}