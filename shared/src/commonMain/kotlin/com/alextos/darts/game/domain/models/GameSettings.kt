package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player
import kotlinx.serialization.Serializable

@Serializable
data class GameSettings(
    val selectedPlayers: List<Player>,
    val selectedGameGoal: Int,
    val isFinishWithDoublesChecked: Boolean,
    val isRandomPlayersOrderChecked: Boolean,
    val isStatisticsEnabled: Boolean,
    val isTurnLimitEnabled: Boolean
) {
    fun getUIGameTitleSettingsString(): String {
        return if (!isFinishWithDoublesChecked) {
            ""
        } else {
            "\uD83C\uDFC1 x2"
        }
    }
}
