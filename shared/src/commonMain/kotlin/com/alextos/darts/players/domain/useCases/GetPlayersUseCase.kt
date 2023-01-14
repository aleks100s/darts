package com.alextos.darts.players.domain.useCases

import com.alextos.darts.players.domain.models.Player
import com.alextos.darts.players.domain.PlayerDataSource
import kotlinx.coroutines.flow.Flow

class GetPlayersUseCase(
    private val dataSource: PlayerDataSource
) {
    fun execute(): Flow<List<Player>> {
        return dataSource.getPlayers()
    }
}