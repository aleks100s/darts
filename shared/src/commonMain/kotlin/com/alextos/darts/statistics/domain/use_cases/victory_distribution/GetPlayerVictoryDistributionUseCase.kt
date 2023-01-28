package com.alextos.darts.statistics.domain.use_cases.victory_distribution

import com.alextos.darts.core.domain.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.PlayerVictoryDistribution

class GetPlayerVictoryDistributionUseCase(
    private val dataSource: StatisticsDataSource
) {

    fun execute(player: Player): PlayerVictoryDistribution {
        return dataSource.getPlayerVictoryDistribution(player)
    }
}