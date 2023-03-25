package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.Player

class GetGameHistoryUseCase(private val dataSource: GameDataSource) {

    fun execute(gameId: Long, players: List<Player>): List<PlayerHistory> {
        val playerHistories = mutableListOf<PlayerHistory>()
        players.forEach { player ->
            val playerHistory = dataSource.getPlayerHistory(gameId, player)
            playerHistories.add(playerHistory)
        }
        return playerHistories
    }
}