package com.alextos.darts.statistics.domain.use_cases.biggest_final_turn

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.StatisticsSet
import kotlinx.coroutines.flow.*

class GetPlayersBiggestFinalTurnUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(players: List<Player>): Flow<List<StatisticsSet>> {
        val flows = players.map { player ->
            dataSource.getPlayerBiggestFinalSet(player)
                .map { set ->
                    set?.let {
                        StatisticsSet(player, set)
                    }
                }
        }
        if (flows.isEmpty()) {
            return flow { emit(emptyList()) }
        }
        return combine(flows) { data ->
            data.toList()
                .mapNotNull { it }
                .sortedByDescending { it.set.score() }
        }
    }
}