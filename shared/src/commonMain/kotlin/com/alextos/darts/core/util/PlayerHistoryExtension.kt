package com.alextos.darts.core.util

import com.alextos.darts.game.domain.models.PlayerHistory

fun List<PlayerHistory>.numberOfTurns(): Int {
    return maxByOrNull { it.turns.count() }?.turns?.count() ?: 0
}