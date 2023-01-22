package com.alextos.darts.core.domain

import kotlinx.coroutines.flow.Flow

interface PlayersDataSource {
    fun getPlayers(): Flow<List<Player>>
}