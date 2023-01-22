package com.alextos.darts.core.domain

data class Set(
    val shots: List<Shot>,
    val leftAfter: Int,
    val isOverkill: Boolean
) {
    fun score(): Int {
        return shots.fold(0) { acc, shot -> acc + (shot.sector.value) }
    }

    fun shotsLeft(): Int = 3 - shots.count()
}
