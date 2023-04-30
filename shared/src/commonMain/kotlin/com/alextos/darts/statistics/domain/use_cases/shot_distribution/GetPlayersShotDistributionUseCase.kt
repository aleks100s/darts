package com.alextos.darts.statistics.domain.use_cases.shot_distribution

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.PlayerShotDistribution
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class GetPlayerShotDistributionUseCase(
    private val statisticsDataSource: StatisticsDataSource
) {
    fun execute(player: Player?): Flow<PlayerShotDistribution> {
        return player?.let { statisticsDataSource.getPlayerShotDistribution(it) }
            ?: run { emptyFlow() }
    }
}