package com.alextos.darts.statistics.domain.use_cases.heatmap

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.statistics.domain.StatisticsDataSource
import com.alextos.darts.statistics.domain.models.SectorHeatmapDistribution

class GetSectorHeatmapUseCase(
    private val dataSource: StatisticsDataSource
) {
    fun execute(player: Player): SectorHeatmapDistribution {
        return dataSource.getSectorHeatmap(player, Sector.heatmapSectors)
    }
}