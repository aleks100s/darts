package com.alextos.darts.statistics.domain.use_cases.heatmap

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.SectorHeat
import com.alextos.darts.statistics.domain.models.SectorHeatmapDistribution

class GetSectorHeatmapUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(player: Player): SectorHeatmapDistribution {
        val list: MutableList<Pair<Sector, Int>> = mutableListOf()
        Sector.heatmapSectors.forEach { sector ->
            val count = dataSource.getSectorCount(player, sector)
            list.add(sector to count)
        }
        list.sortByDescending { it.second }
        val maxCount = (list.firstOrNull()?.second ?: 0).toFloat()
        val heatmap = list.map { SectorHeat(it.first, count = it.second, heat = it.second.toFloat() / maxCount) }
        return SectorHeatmapDistribution(
            heatmap = heatmap,
            player = player
        )
    }
}