package com.alextos.darts.statistics.presentation.time

import com.alextos.darts.statistics.domain.models.TimeDuration

data class TimeState(
    val totalTimePlayed: TimeDuration? = null,
    val isLoading: Boolean = true
) {
    val isEmpty: Boolean
        get() { return totalTimePlayed == null }
}