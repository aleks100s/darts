package com.alextos.darts.core.domain.model

data class Set(
    val shots: List<Shot>,
    val leftAfter: Int,
    val isOverkill: Boolean
) {
    fun score(): Int {
        return shots.fold(0) { acc, shot -> acc + (shot.sector.value) }
    }

    fun shotsLeft(): Int = 3 - shots.count()

    fun numberOfMisses(): Int {
        return shots.count { it.sector == Sector.Miss }
    }
}
