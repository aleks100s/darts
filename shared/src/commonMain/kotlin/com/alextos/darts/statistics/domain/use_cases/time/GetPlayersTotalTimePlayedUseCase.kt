package com.alextos.darts.statistics.domain.use_cases.time

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.PlayerTimeDuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class GetPlayersTotalTimePlayedUseCase(
    private val dataSource: StatisticsDataSource,
    private val converter: TimeDurationConverter
) {
    fun execute(players: List<Player>): Flow<List<PlayerTimeDuration>> {
        val flows = players.map { player ->
            dataSource.getPlayerTotalTimePlayed(player)
                .mapNotNull { it ?: 0.0 }
                .map { converter.convert(it) }
                .map { PlayerTimeDuration(player, it) }
        }
        if (flows.isEmpty()) {
            return flow { emit(emptyList()) }
        }
        return combine(flows) { data ->
            data.toList()
                .sortedByDescending { it }
        }
    }
}