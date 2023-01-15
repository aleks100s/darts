package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.Game
import com.alextos.darts.game.domain.models.GameHistory
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.players.domain.models.Player

class GetGameHistoryUseCase(private val dataSource: GameDataSource) {

    fun execute(gameId: Long, players: List<Player>): List<PlayerHistory> {
        return dataSource.getGameHistory(gameId, players)
    }
}