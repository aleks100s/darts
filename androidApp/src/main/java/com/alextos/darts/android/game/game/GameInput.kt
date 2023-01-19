package com.alextos.darts.android.game.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.extensions.color
import com.alextos.darts.android.common.presentation.extensions.textColor
import com.alextos.darts.android.game.game.components.CurrentSetItem
import com.alextos.darts.game.domain.models.Sector
import com.alextos.darts.game.domain.models.Set

@Composable
fun GameInput(
    currentSet: Set,
    playerName: String,
    leaderScore: Int,
    onClick: (Sector) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CurrentSetItem(currentSet = currentSet, player = playerName, leaderScore = leaderScore)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(Sector.sectors) {
                InputRow(sectors = it, onClick = onClick)
            }
        }
    }
}

@Composable
private fun InputRow(sectors: List<Sector>, onClick: (Sector) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        sectors.forEach {
            InputCell(sector = it, modifier = Modifier.weight(1f)) {
                onClick(it)
            }
        }
    }
}

@Composable
private fun InputCell(
    modifier: Modifier,
    sector: Sector,
    onClick: () -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = { onClick() },
            colors = textButtonColors(
                backgroundColor = sector.color(),
                contentColor = sector.textColor()
            )
        ) {
            Text(text = sector.uiString())
        }
    }
}