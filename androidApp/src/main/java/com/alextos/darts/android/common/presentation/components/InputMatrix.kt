package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.extensions.color
import com.alextos.darts.android.common.presentation.extensions.inputString
import com.alextos.darts.android.common.presentation.extensions.textColor
import com.alextos.darts.core.domain.model.Sector

@Composable
fun InputMatrix(onInputClick: (Sector) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(Sector.sectors) { sectors ->
            InputRow(sectors = sectors, onInputClick = onInputClick)
        }
        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
private fun InputRow(sectors: List<Sector>, onInputClick: (Sector) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        sectors.forEach {
            InputCell(sector = it, modifier = Modifier.weight(1f)) {
                onInputClick(it)
            }
        }
    }
}

@Composable
private fun InputCell(
    modifier: Modifier,
    sector: Sector,
    onInputClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(sector.color())
            .border(1.dp, Color.DarkGray)
            .clickable { onInputClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = sector.inputString(), color = sector.textColor())
    }
}