package com.alextos.darts.game.domain.useCases

import com.alextos.darts.core.domain.PlayersDataSource

class CreatePlayerUseCase(
    private val dataSource: PlayersDataSource
) {

    fun execute(name: String) {
        return dataSource.createPlayer(name = name.trim())
    }
}