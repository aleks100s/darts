package com.alextos.darts.core.domain

class DeletePlayerUseCase(
    private val dataSource: PlayersDataSource
) {

    suspend fun execute(player: Player) {
        dataSource.deletePlayer(player)
    }
}