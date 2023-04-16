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
        return combine(flows) { data ->
            data.asList()
                .mapNotNull { it }
                .sortedByDescending { it.set.score() }
        }
    }
}