package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.extensions.rowColor
import com.alextos.darts.android.common.presentation.extensions.textColor
import com.alextos.darts.core.domain.model.Turn

@Composable
fun TurnItem(
    turn: Turn,
    useTurnColors: Boolean,
    onSelect: () -> Unit
) {
    val textColor = turn.textColor()
    val backgroundColor = if (useTurnColors) turn.rowColor() else Color.Transparent
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onSelect() }
            .padding(16.dp)
    ) {
        turn.shots.forEach {
            Cell(
                modifier = Modifier.weight(1f),
                text = it.sector.valueString(),
                textColor = textColor
            )
        }
        (0 until turn.shotsLeft()).forEach { _ ->
            Cell(
                modifier = Modifier.weight(1f),
                text = "",
                textColor = textColor
            )
        }
        Cell(
            modifier = Modifier.weight(1f),
            text = turn.score().toString(),
            textColor = textColor
        )
        Cell(
            modifier = Modifier.weight(1f),
            text = turn.leftAfter.toString(),
            textColor = textColor
        )
    }
}

