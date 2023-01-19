package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.GameDataSource

class CreatePlayerUseCase(
    private val dataSource: GameDataSource
) {

    fun execute(name: String) {
        return dataSource.createPlayer(name = name.trim())
    }
}