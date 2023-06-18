package com.alextos.darts.statistics.domain.models

import com.alextos.darts.core.domain.model.Player

data class PlayerTimeDuration(
    val player: Player,
    val duration: TimeDuration
): Comparable<PlayerTimeDuration> {
    override fun compareTo(other: PlayerTimeDuration): Int {
        return duration.compareTo(other.duration)
    }
}