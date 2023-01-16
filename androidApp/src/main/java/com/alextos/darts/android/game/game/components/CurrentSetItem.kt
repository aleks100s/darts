package com.alextos.darts.android.game.game.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.alextos.darts.android.common.presentation.components.PlayerHistoryHeader
import com.alextos.darts.android.common.presentation.components.SectionHeader
import com.alextos.darts.android.common.presentation.components.SetItem
import com.alextos.darts.game.domain.models.Set

@Composable
fun CurrentSetItem(currentSet: Set, player: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SectionHeader(title = player)
        PlayerHistoryHeader()
        SetItem(set = currentSet)
    }
}