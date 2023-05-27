package com.alextos.darts.statistics.domain.use_cases.average_values

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.AveragePlayerValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class GetPlayersAverageValuesUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(players: List<Player>): Flow<List<AveragePlayerValue>> {
        val flows = players.map { player ->
            val setScoreFlow = dataSource.getPlayerAverageTurnScore(player)
            val shotValueFlow = dataSource.getPlayerAverageShotValue(player)
            setScoreFlow.combine(shotValueFlow) { setScore, shotValue ->
                if (setScore != null && shotValue != null) {
                    AveragePlayerValue(
                        player = player,
                        setScore = setScore,
                        shotValue = shotValue
                    )
                } else null
            }
        }
        if (flows.isEmpty()) {
            return flow { emit(emptyList()) }
        }
        return combine(flows) { data ->
            data.toList()
                .mapNotNull { it }
                .sortedByDescending { it.shotValue }
                .sortedByDescending { it.setScore }
        }
    }
}