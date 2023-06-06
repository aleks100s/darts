package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player
import kotlinx.serialization.Serializable

@Serializable
data class GameSettings(
    val selectedPlayers: List<Player>,
    val selectedGameGoal: Int,
    val isFinishWithDoublesChecked: Boolean,
    val isRandomPlayersOrderChecked: Boolean,
    val isStatisticsDisabled: Boolean
) {
    fun getUIGameTitleSettingsString(): String {
        if (!isFinishWithDoublesChecked) {
            return ""
        }
        val settings = mutableListOf<String>()
        if (isFinishWithDoublesChecked) {
            settings.add("\uD83C\uDFC1 x2")
        }
        return " (${settings.joinToString(separator = ", ")})"
    }
}
