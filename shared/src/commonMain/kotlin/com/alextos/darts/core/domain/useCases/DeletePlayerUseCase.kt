package com.alextos.darts.core.domain.useCases

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.PlayersDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeletePlayerUseCase(
    private val dataSource: PlayersDataSource
) {

    suspend fun execute(player: Player) {
        dataSource.deletePlayer(player)
    }
}