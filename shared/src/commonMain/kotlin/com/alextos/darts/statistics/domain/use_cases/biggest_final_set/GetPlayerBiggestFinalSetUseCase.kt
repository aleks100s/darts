package com.alextos.darts.statistics.domain.use_cases.biggest_final_set

import com.alextos.darts.game.domain.models.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.StatisticsSet

class GetPlayerBiggestFinalSetUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(players: List<Player>): List<StatisticsSet> {
        return players.mapNotNull { player ->
            dataSource.getPlayerBiggestFinalSet(player)?.let { set ->
                StatisticsSet(player = player, set)
            }
        }
    }
}