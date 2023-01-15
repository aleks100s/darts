package com.alextos.darts.android.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.game.domain.models.Game

@Composable
fun Game.getTitle(): String {
    return stringResource(id = R.string.game_title, id ?: 0)
}