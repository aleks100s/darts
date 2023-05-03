package com.alextos.darts.game.domain.useCases

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPlayerAverageTurnUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(player: Player): Flow<Pair<Player, Int>> {
        return dataSource.getPlayerAverageTurnScore(player)
            .map { it?.toInt() ?: 0 }
            .map { player to it }
    }
}