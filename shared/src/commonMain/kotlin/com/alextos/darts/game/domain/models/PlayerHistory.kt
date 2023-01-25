package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.Player
import com.alextos.darts.core.domain.Set

data class PlayerHistory(
    val player: Player,
    val turns: List<Set> = listOf()
) {
    fun chartData(size: Int): List<Float> {
        val firstSet = turns.firstOrNull()
        val firstValue = (firstSet?.score() ?: 0) + (firstSet?.leftAfter ?: 0)
        val additionalValues = (0..(size - (turns.count() + 1))).map { 0f }
        return listOf(firstValue.toFloat()) +
                turns.map { it.leftAfter.toFloat() } +
                additionalValues
    }
}
