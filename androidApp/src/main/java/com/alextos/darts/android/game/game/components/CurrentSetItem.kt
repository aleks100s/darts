package com.alextos.darts.android.game.game.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.alextos.darts.android.common.presentation.components.PlayerHistoryHeader
import com.alextos.darts.android.common.presentation.components.SectionHeader
import com.alextos.darts.android.common.presentation.components.SetItem
import com.alextos.darts.core.domain.Set

@Composable
fun CurrentSetItem(currentSet: Set, player: String, leaderScore: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SectionHeader(title = player)
        LeaderItem(score = leaderScore)
        PlayerHistoryHeader()
        SetItem(set = currentSet, onSelect = {})
    }
}