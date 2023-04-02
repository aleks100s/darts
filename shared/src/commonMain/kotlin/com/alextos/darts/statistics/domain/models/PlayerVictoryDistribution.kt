package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.model.Player

data class PlayerVictoryDistribution(
    val player: Player,
    val gamesCount: Int,
    val victoriesCount: Int
) {
    fun victoryPercent(): Float {
        return (victoriesCount.toFloat() / gamesCount.toFloat()) * 100
    }

    fun losePercent(): Float {
        return ((gamesCount - victoriesCount).toFloat() / gamesCount.toFloat()) * 100
    }

    fun isEmpty(): Boolean {
        return gamesCount == 0
    }
}