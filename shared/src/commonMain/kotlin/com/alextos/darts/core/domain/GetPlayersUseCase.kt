package com.alextos.darts.core.domain

import kotlinx.coroutines.flow.Flow

class GetPlayersUseCase(
    private val dataSource: PlayersDataSource
) {
    fun execute(): Flow<List<Player>> {
        return dataSource.getPlayers()
    }
}