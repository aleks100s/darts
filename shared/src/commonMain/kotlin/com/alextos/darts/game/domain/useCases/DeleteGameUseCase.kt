package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.Game

class DeleteGameUseCase(
    private val dataSource: GameDataSource
) {

    suspend fun execute(game: Game) {
        dataSource.deleteGame(game)
    }
}