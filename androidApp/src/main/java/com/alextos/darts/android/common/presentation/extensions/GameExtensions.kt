package com.alextos.darts.android.common.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.game.domain.models.Game

@Composable
fun Game.getTitle(): String {
    return if (winner == null) {
        stringResource(id = R.string.training, id ?: 0, gameGoal)
    } else {
        stringResource(id = R.string.game_title, id ?: 0, gameGoal)
    }
}