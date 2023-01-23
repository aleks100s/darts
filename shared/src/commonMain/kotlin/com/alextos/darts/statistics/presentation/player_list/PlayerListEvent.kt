package com.alextos.darts.statistics.presentation.player_list

import com.alextos.darts.core.domain.Player

sealed class PlayerListEvent {
    data class SelectPlayer(val player: Player): PlayerListEvent()
}