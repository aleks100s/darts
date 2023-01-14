package com.alextos.darts.players.domain.useCases

import com.alextos.darts.players.domain.models.Player
import com.alextos.darts.core.util.CommonFlow
import com.alextos.darts.players.domain.PlayerDataSource

class GetPlayersUseCase(
    private val dataSource: PlayerDataSource
) {
    fun execute(): CommonFlow<List<Player>> {
        return dataSource.getPlayers()
    }
}