package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.GameDataSource
import kotlinx.coroutines.flow.Flow

class GetGamesUseCase(
    private val dataSource: GameDataSource
) {

    fun execute(): Flow<List<Game>> {
        return dataSource.getGames()
    }
}