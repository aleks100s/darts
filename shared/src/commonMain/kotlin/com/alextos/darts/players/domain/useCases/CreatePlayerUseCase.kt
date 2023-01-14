package com.alextos.darts.players.domain.useCases

import com.alextos.darts.players.domain.models.Player
import com.alextos.darts.players.domain.PlayerDataSource

class CreatePlayerUseCase(
    private val dataSource: PlayerDataSource
) {

    fun execute(name: String): Player {
        return dataSource.createPlayer(name = name)
    }
}