package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.model.Player

data class SectorHeatmapDistribution(
    val player: Player,
    val heatmap: List<SectorHeat> = listOf()
) {
    fun isEmpty(): Boolean {
        return heatmap.isEmpty()
    }

    fun getOuters(): List<SectorHeat> {
        return heatmap.filter { it.sector.isInner() || it.sector.isOuter() }
    }

    fun getInners(): List<SectorHeat> {
        return heatmap.filter { it.sector.isInner() || it.sector.isOuter() }
    }

    fun getDoubles(): List<SectorHeat> {
        return heatmap.filter { it.sector.isDouble() }
    }

    fun getTriples(): List<SectorHeat> {
        return heatmap.filter { it.sector.isTriplet() }
    }

    fun getBullseye(): SectorHeat? {
        return heatmap.firstOrNull { it.sector.isBullseye() }
    }

    fun getDoubleBullseye(): SectorHeat? {
        return heatmap.firstOrNull { it.sector.isDoubleBullseye() }
    }
}