package com.alextos.darts.game.domain.useCases

import com.alextos.darts.core.domain.PlayersDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreatePlayerUseCase(
    private val dataSource: PlayersDataSource
) {
    suspend fun execute(name: String) {
        dataSource.createPlayer(name = name.trim())
    }
}