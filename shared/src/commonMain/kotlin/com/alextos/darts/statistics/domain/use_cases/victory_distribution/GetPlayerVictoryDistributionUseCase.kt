package com.alextos.darts.statistics.domain.use_cases.victory_distribution

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.PlayerVictoryDistribution
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetPlayerVictoryDistributionUseCase(
    private val dataSource: StatisticsDataSource
) {

    fun execute(player: Player?): Flow<PlayerVictoryDistribution> {
        return player?.let { dataSource.getPlayerVictoryDistribution(it) }
            ?: run { emptyFlow() }
    }
}