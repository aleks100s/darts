package com.alextos.darts.android.game.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.Cell
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
        HintRow()
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(Sector.sectors) {
                InputRow(sectors = it, onClick = onClick)
            }
        }
    }
}

@Composable
private fun HintRow() {
    val strings = listOf(
        stringResource(id = R.string.inner),
        stringResource(id = R.string.outer),
        stringResource(id = R.string.double_),
        stringResource(id = R.string.triplet)
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        for (string in strings) {
            Cell(modifier = Modifier.weight(1f), text = string)
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
    Row(
        modifier = modifier
            .background(sector.color())
            .border(1.dp, Color.DarkGray)
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = sector.uiString(), color = sector.textColor())
    }
}