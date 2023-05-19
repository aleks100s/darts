package com.alextos.darts.statistics.presentation.average_values

import com.alextos.darts.statistics.domain.models.AveragePlayerValue

data class AverageValuesState(
    val averageTurnOfAll: Double = 0.0,
    val averageShotOfAll: Double = 0.0,
    val playersAverageValues: List<AveragePlayerValue> = listOf(),
    val isLoading: Boolean = true
) {
    fun isEmpty() = averageShotOfAll == 0.0
}