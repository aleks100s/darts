package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.game.domain.GameDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveGameHistoryUseCase(
    private val dataSource: GameDataSource
) {

    suspend fun execute(gameHistory: GameHistory) {
        dataSource.saveGameHistory(gameHistory)
    }
}