package com.alextos.darts.statistics.domain.use_cases.victory_distribution

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.PlayerVictoryDistribution
import kotlinx.coroutines.flow.Flow

class GetPlayerVictoryDistributionUseCase(
    private val dataSource: StatisticsDataSource
) {

    fun execute(player: Player): Flow<PlayerVictoryDistribution> {
        return dataSource.getPlayerVictoryDistribution(player)
    }
}