package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.extensions.rowColor
import com.alextos.darts.core.domain.model.Set

@Composable
fun SetItem(set: Set, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(set.rowColor())
            .clickable { onSelect() }
            .padding(16.dp)
    ) {
        set.shots.forEach {
            Cell(modifier = Modifier.weight(1f), text = it.sector.valueString())
        }
        (0 until set.shotsLeft()).forEach { _ ->
            Cell(modifier = Modifier.weight(1f), text = "")
        }
        Cell(modifier = Modifier.weight(1f), text = set.score().toString())
        Cell(modifier = Modifier.weight(1f), text = set.leftAfter.toString())
    }
}

