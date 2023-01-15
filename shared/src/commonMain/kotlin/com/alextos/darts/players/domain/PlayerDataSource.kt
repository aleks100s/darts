package com.alextos.darts.players.domain

import com.alextos.darts.players.domain.models.Player
import kotlinx.coroutines.flow.Flow

interface PlayerDataSource {
    fun getPlayers(): Flow<List<Player>>
    fun createPlayer(name: String)
}