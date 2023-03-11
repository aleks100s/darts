package com.alextos.darts.core.domain

import kotlinx.coroutines.flow.Flow

interface PlayersDataSource {
    fun getPlayers(): Flow<List<Player>>
    fun createPlayer(name: String)
    fun deletePlayer(player: Player)
}