package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.PlayerHistoryHeader
import com.alextos.darts.android.common.presentation.components.SectionHeader
import com.alextos.darts.android.common.presentation.components.SectionSubHeader
import com.alextos.darts.android.common.presentation.components.TurnItem
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
            PlayerHistoryHeader()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(playerHistory.turns) { turn ->
                    TurnItem(turn = turn, useTurnColors = true) {
                        onSelect(turn)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }
    }
}