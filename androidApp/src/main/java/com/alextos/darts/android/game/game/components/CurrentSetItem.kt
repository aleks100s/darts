package com.alextos.darts.android.game.game.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.components.PlayerHistoryHeader
import com.alextos.darts.android.common.presentation.components.SetItem
import com.alextos.darts.game.domain.models.Set

@Composable
fun CurrentSetItem(currentSet: Set, player: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = player, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.h1)
        PlayerHistoryHeader()
        SetItem(set = currentSet)
    }
}