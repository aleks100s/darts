package com.alextos.darts.statistics.domain.use_cases.heatmap

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.SectorHeat
import com.alextos.darts.statistics.domain.models.SectorHeatmapDistribution
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSectorHeatmapUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(player: Player): Flow<SectorHeatmapDistribution?> {
        val flows = Sector.heatmapSectors.map { sector ->
            dataSource.getSectorCount(player, sector)
        }
        return combine(flows) { data ->
            val list = data.toList()
                .sortedByDescending { it.second }
            val maxCount = (list.firstOrNull()?.second ?: 0).toFloat()

            return@combine if (maxCount == 0f) {
                null
            } else {
                val heatmap = list.map { SectorHeat(it.first, count = it.second, heat = it.second.toFloat() / maxCount) }
                SectorHeatmapDistribution(
                    heatmap = heatmap,
                    player = player
                )
            }
        }
    }
}