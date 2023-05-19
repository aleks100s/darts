package com.alextos.darts.game.presentation.game_list

import com.alextos.darts.game.domain.models.Game

data class GameListState(
    val games: List<Game> = listOf(),
    val isDeleteGameDialogShown: Boolean = false,
    val selectedGame: Game? = null,
    val isLoading: Boolean = true,
    val isActionsDialogShown: Boolean = false
)