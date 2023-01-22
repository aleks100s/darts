package com.alextos.darts.statistics.domain.use_cases.average_values

import com.alextos.darts.core.domain.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.AveragePlayerValue

class GetPlayersAverageValuesUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(players: List<Player>): List<AveragePlayerValue> {
        return players.mapNotNull { player ->
            val setScore = dataSource.getPlayerAverageSetScore(player)
            val shotValue = dataSource.getPlayerAverageShotValue(player)
            if (setScore != null && shotValue != null) {
                AveragePlayerValue(
                    player = player,
                    setScore = setScore,
                    shotValue = shotValue
                )
            } else null
        }
    }
}