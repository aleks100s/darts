package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.core.util.CommonFlow
import com.alextos.darts.game.domain.GameDataSource

class GetGamesUseCase(
    private val dataSource: GameDataSource
) {

    fun execute(): CommonFlow<List<Game>> {
        return dataSource.getGames()
    }
}