package com.alextos.darts.statistics.domain.use_cases.time

import com.alextos.darts.statistics.domain.models.TimeDuration

class TimeDurationConverter {
    fun convert(milliseconds: Double): TimeDuration {
        val totalSeconds = milliseconds / 1000
        val hours = totalSeconds / 3600
        val hourReminder = (totalSeconds % 3600)
        val minutes = hourReminder / 60
        val seconds = hourReminder % 60
        return TimeDuration(
            hours = hours.toInt(),
            minutes = minutes.toInt(),
            seconds = seconds.toInt()
        )
    }
}