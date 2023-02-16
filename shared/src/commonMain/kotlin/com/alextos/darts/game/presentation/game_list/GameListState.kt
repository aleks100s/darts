package com.alextos.darts.game.presentation.game_list

import com.alextos.darts.game.domain.models.Game

data class GameListState(
    val games: List<Game> = listOf(),
    val isDeleteGameDialogShown: Boolean = false,
    val gameToDelete: Game? = null
)