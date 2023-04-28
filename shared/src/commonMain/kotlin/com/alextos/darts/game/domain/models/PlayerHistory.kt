package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player
import com.alextos.darts.core.domain.model.Turn

data class PlayerHistory(
    val player: Player,
    val turns: List<Turn> = listOf()
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

    fun biggestSet(): Turn? {
        return turns.maxByOrNull { it.score() }
    }

    fun smallestSet(): Turn? {
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

    fun average(): Int {
        val sum =  turns.sumOf { it.score() }
        val count = turns.count()
        return if (count != 0) {
            sum / count
        } else {
            0
        }
    }
}
