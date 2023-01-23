package com.alextos.darts.statistics.domain.use_cases.biggest_final_set

import com.alextos.darts.core.domain.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.StatisticsSet

class GetPlayersBiggestFinalSetUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(players: List<Player>): List<StatisticsSet> {
        return players.mapNotNull { player ->
            dataSource.getPlayerBiggestFinalSet(player)?.let { set ->
                StatisticsSet(player = player, set)
            }
        }.sortedByDescending { it.set.score() }
    }
}