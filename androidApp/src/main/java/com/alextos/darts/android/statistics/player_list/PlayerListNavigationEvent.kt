package com.alextos.darts.android.statistics.player_list

import com.alextos.darts.core.domain.model.Player

sealed class PlayerListNavigationEvent {
    data class SelectNavigationPlayer(val player: Player): PlayerListNavigationEvent()
}
