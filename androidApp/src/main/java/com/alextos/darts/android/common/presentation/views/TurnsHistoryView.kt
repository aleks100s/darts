package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.components.PlayerHistoryHeader
import com.alextos.darts.android.common.presentation.components.TurnItem
import com.alextos.darts.core.domain.model.Turn

@Composable
fun TurnsHistoryView(
    turns: List<Turn>,
    useTurnColors: Boolean,
    onSelect: (Turn) -> Unit
) {
    Column {
        PlayerHistoryHeader()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(turns) { turn ->
                TurnItem(turn = turn, useTurnColors = useTurnColors) {
                    onSelect(turn)
                }
            }
            item {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }
}