package com.alextos.darts.statistics.presentation.player_list

import com.alextos.darts.core.domain.model.Player

data class PlayerListState(
    val players: List<Player> = listOf()
)