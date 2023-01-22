package com.alextos.darts.statistics.domain.use_cases.most_frequent_shots

import com.alextos.darts.game.domain.models.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.StatisticsShots

class GetPlayersMostFrequentShotsUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(players: List<Player>): List<StatisticsShots> {
        return players.mapNotNull { player ->
            val shots = dataSource.getPlayerMostFrequentShots(player)
            if (shots.isNotEmpty()) {
                StatisticsShots(player, shots)
            } else null
        }
    }
}