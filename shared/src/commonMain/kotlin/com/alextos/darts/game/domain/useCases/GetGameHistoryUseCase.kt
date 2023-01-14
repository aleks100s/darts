package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.GameHistory

class GetGameHistoryUseCase(
    private val dataSource: GameDataSource
) {

    fun execute(gameHistory: GameHistory) {
        dataSource.saveGameHistory(gameHistory)
    }
}