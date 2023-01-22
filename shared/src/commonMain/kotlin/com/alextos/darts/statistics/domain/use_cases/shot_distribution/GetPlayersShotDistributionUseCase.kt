package com.alextos.darts.statistics.domain.use_cases.shot_distribution

import com.alextos.darts.core.domain.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.PlayerShotDistribution

class GetPlayersShotDistributionUseCase(
    private val statisticsDataSource: StatisticsDataSource
) {
    fun execute(players: List<Player>): List<PlayerShotDistribution> {
        return players.map { player ->
            statisticsDataSource.getPlayerShotDistribution(player)
        }
    }
}