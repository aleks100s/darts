package com.alextos.darts.statistics.presentation.time

import com.alextos.darts.statistics.domain.models.PlayerTimeDuration
import com.alextos.darts.statistics.domain.models.TimeDuration

data class TimeState(
    val totalTimePlayed: TimeDuration? = null,
    val playersDuration: List<PlayerTimeDuration> = listOf(),
    val isLoading: Boolean = true
) {
    val isEmpty: Boolean
        get() { return totalTimePlayed == null }
}