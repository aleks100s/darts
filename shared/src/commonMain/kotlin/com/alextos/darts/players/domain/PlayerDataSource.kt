package com.alextos.darts.players.domain

import com.alextos.darts.core.util.CommonFlow
import com.alextos.darts.players.domain.models.Player

interface PlayerDataSource {
    fun getPlayers(): CommonFlow<List<Player>>
    fun createPlayer(name: String): Player
}