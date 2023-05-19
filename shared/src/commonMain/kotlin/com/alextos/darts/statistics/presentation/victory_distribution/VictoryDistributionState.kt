package com.alextos.darts.statistics.presentation.victory_distribution

import com.alextos.darts.statistics.domain.models.PlayerVictoryDistribution

data class VictoryDistributionState(
    val distribution: PlayerVictoryDistribution? = null,
    val isLoading: Boolean = true
)