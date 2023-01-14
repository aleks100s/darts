package com.alextos.darts.game.domain.models

data class Set(
    val shots: List<Shot>,
    val gameOrder: Int,
    val leftAfter: Int,
    val isOverkill: Boolean
) {
    fun score(): Int {
        return shots.fold(0) { acc, shot -> acc + shot.sector.value }
    }
}
