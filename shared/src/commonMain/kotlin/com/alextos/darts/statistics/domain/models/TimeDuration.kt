package com.alextos.darts.statistics.domain.models

data class TimeDuration(
    val hours: Int,
    val minutes: Int,
    val seconds: Int
): Comparable<TimeDuration> {
    override fun compareTo(other: TimeDuration): Int {
        val thisSeconds = this.toSeconds()
        val otherSeconds = other.toSeconds()
        return if (thisSeconds > otherSeconds) {
            1
        } else if (thisSeconds < otherSeconds) {
            -1
        } else {
            0
        }
    }

    private fun toSeconds(): Int {
        return hours * 3600 + minutes * 60 + seconds
    }
}
