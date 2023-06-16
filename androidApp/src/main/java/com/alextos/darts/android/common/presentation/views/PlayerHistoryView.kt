package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.SectionHeader
import com.alextos.darts.android.common.presentation.components.SectionSubHeader
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.model.Turn

@Composable
fun PlayerHistoryView(
    playerHistory: PlayerHistory,
    goal: Int,
    onSelect: (Turn) -> Unit
) {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            SectionHeader(title = playerHistory.player.name)
            SectionSubHeader(subtitle = stringResource(id = R.string.game_goal_value, goal))
            TurnsHistoryView(
                turns = playerHistory.turns,
                useTurnColors = true,
                onSelect = onSelect
            )
        }
    }
}