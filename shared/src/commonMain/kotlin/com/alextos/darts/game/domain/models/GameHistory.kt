package com.alextos.darts.game.domain.models

data class GameHistory(
    val game: Game,
    val playerHistories: List<PlayerHistory>
) {
}