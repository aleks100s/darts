package com.alextos.darts.game.domain.models

import com.alextos.darts.core.domain.model.Player
import kotlinx.serialization.Serializable

@Serializable
data class GameSettings(
    val selectedPlayers: List<Player>,
    val selectedGameGoal: Int,
    val isFinishWithDoublesChecked: Boolean
)
