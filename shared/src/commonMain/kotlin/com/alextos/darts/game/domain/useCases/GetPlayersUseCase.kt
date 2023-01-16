package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.Player
import kotlinx.coroutines.flow.Flow

class GetPlayersUseCase(
    private val dataSource: GameDataSource
) {
    fun execute(): Flow<List<Player>> {
        return dataSource.getPlayers()
    }
}