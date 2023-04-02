package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Set

data class PlayerHistory(
    val player: Player,
    val turns: List<Set> = listOf()
) {
    fun chartData(size: Int): List<Float> {
        val firstSet = turns.firstOrNull()
        val firstValue = (firstSet?.score() ?: 0) + (firstSet?.leftAfter ?: 0)
        val lastValue = turns.lastOrNull()?.leftAfter?.toFloat() ?: 0f
        val additionalValues = (0..(size - (turns.count() + 1))).map { lastValue }
        return listOf(firstValue.toFloat()) +
                turns.map { it.leftAfter.toFloat() } +
                additionalValues
    }

    fun biggestSet(): Set? {
        return turns.maxByOrNull { it.score() }
    }

    fun smallestSet(): Set? {
        return turns.minByOrNull { it.score() }
    }

    fun numberOfMisses(): Int {
        return turns.fold(0) { sum, set ->
            sum + set.numberOfMisses()
        }
    }

    fun numberOfOverkills(): Int {
        return turns.count { it.isOverkill }
    }
}
