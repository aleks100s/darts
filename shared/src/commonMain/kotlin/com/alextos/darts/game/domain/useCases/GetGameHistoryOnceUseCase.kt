package com.alextos.darts.game.domain.useCases

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.PlayerHistory

class GetGameHistoryOnceUseCase(private val dataSource: GameDataSource) {
    suspend fun execute(gameId: Long, players: List<Player>): List<PlayerHistory> {
        return players.map { player ->
            dataSource.getPlayerHistoryOnce(gameId, player)
        }
    }
}