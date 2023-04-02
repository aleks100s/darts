package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteGameUseCase(
    private val dataSource: GameDataSource
) {

    suspend fun execute(game: Game) {
        withContext(Dispatchers.Default) {
            dataSource.deleteGame(game)
        }
    }
}