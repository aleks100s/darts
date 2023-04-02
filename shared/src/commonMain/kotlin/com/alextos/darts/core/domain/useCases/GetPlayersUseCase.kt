package com.alextos.darts.core.domain.useCases

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.PlayersDataSource
import kotlinx.coroutines.flow.Flow

class GetPlayersUseCase(
    private val dataSource: PlayersDataSource
) {
    fun execute(): Flow<List<Player>> {
        return dataSource.getPlayers()
    }
}