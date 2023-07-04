package com.alextos.darts.game.domain.useCases

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.PlayerHistory

class RestoreGameHistoryUseCase(private val dataSource: GameDataSource) {
    suspend fun execute(game: Game, players: List<Player>): List<PlayerHistory> {
        val result = players.map { player ->
            dataSource.getPlayerHistoryOnce(game.id ?: 0, player)
        }
        dataSource.deleteGame(game)
        return result
    }
}