package com.alextos.darts.statistics.domain.use_cases.best_turn

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.StatisticsSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GetPlayersBestTurnsUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(players: List<Player>): Flow<List<StatisticsSet>> {
        val flows = players.map { player ->
            dataSource.getPlayerBestSet(player)
                .map { set ->
                    set?.let {
                        StatisticsSet(player, it)
                    }
                }
        }
        return combine(flows) { data ->
            data.toList()
                .mapNotNull { it }
                .sortedByDescending { it.set.score() }
        }
    }
}