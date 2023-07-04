package com.alextos.darts.game.domain.useCases

import com.alextos.darts.game.domain.GameDataSource
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetGameHistoryFlowUseCase(private val dataSource: GameDataSource) {

    fun execute(gameId: Long, players: List<Player>): Flow<List<PlayerHistory>> {
        return combine(
            players.map { player ->
                dataSource.getPlayerHistory(gameId, player)
            }
        ) {
            it.asList()
        }
    }
}