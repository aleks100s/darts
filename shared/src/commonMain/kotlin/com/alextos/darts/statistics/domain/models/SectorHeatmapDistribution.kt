package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Sector

data class SectorHeatmapDistribution(
    val player: Player,
    val distribution: Map<Sector, Int> = mapOf(),
    val shotsTotal: Int = 0
) {
    fun getOuters(): List<SectorHeat> {
        return distribution.keys.filter { it.isInner() || it.isOuter() }
            .map { SectorHeat(it, getSectorFrequency(it)) }
    }

    fun getInners(): List<SectorHeat> {
        return distribution.keys.filter { it.isInner() || it.isOuter() }
            .map { SectorHeat(it, getSectorFrequency(it)) }
    }

    fun getDoubles(): List<SectorHeat> {
        return distribution.keys.filter { it.isDouble() }
            .map { SectorHeat(it, getSectorFrequency(it)) }
    }

    fun getTriples(): List<SectorHeat> {
        return distribution.keys.filter { it.isTriplet() }
            .map { SectorHeat(it, getSectorFrequency(it)) }
    }

    fun getBullseye(): SectorHeat? {
        return distribution.keys.filter { it.isBullseye() }
            .map { SectorHeat(it, getSectorFrequency(it)) }
            .firstOrNull()
    }

    fun getDoubleBullseye(): SectorHeat? {
        return distribution.keys.filter { it.isDoubleBullseye() }
            .map { SectorHeat(it, getSectorFrequency(it)) }
            .firstOrNull()
    }

    private fun getSectorFrequency(sector: Sector): Float {
        if (shotsTotal == 0) {
            return 0f
        }
        return (distribution[sector]?.toFloat() ?: 0f) / shotsTotal
    }
}