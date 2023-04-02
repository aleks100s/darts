package com.alextos.darts.core.domain

import com.alextos.darts.core.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayersDataSource {
    fun getPlayers(): Flow<List<Player>>
    suspend fun createPlayer(name: String)
    suspend fun deletePlayer(player: Player)
}