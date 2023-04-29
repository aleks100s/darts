package com.alextos.darts.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Turn(
    val shots: List<Shot>,
    val leftAfter: Int,
    val isOverkill: Boolean
) {
    companion object {
        const val turnLimit = 3
    }

    fun score(): Int {
        return shots.fold(0) { acc, shot -> acc + (shot.sector.value) }
    }

    fun shotsLeft(): Int = turnLimit - shots.count()

    fun numberOfMisses(): Int {
        return shots.count { it.sector == Sector.Miss }
    }
}
