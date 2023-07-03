package com.alextos.darts.game.presentation.game

import kotlinx.serialization.Serializable

@Serializable
sealed class GameResult {
    object TrainingFinished: GameResult()
    object Draw: GameResult()
    object GamePaused: GameResult()
    data class Winner(val name: String): GameResult()

    val winnerName: String?
        get() {
            return when (this) {
                is Winner -> name
                else -> null
            }
        }
}
