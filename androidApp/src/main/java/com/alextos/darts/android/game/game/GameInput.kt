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
import com.alextos.darts.android.common.presentation.color
import com.alextos.darts.android.common.presentation.textColor
import com.alextos.darts.android.game.game.components.CurrentSetItem
import com.alextos.darts.game.domain.models.Sector
import com.alextos.darts.game.domain.models.Set

val sectors = listOf(
    listOf(Sector.SingleBullseye, Sector.DoubleBullseye, Sector.Miss),
    listOf(Sector.SingleInner1, Sector.Triple1, Sector.SingleOuter1, Sector.Double1),
    listOf(Sector.SingleInner18, Sector.Triple18, Sector.SingleOuter18, Sector.Double18),
    listOf(Sector.SingleInner4, Sector.Triple4, Sector.SingleOuter4, Sector.Double4),
    listOf(Sector.SingleInner13, Sector.Triple13, Sector.SingleOuter13, Sector.Double13),
    listOf(Sector.SingleInner6, Sector.Triple6, Sector.SingleOuter6, Sector.Double6),
    listOf(Sector.SingleInner10, Sector.Triple10, Sector.SingleOuter10, Sector.Double10),
    listOf(Sector.SingleInner15, Sector.Triple15, Sector.SingleOuter15, Sector.Double15),
    listOf(Sector.SingleInner2, Sector.Triple2, Sector.SingleOuter2, Sector.Double2),
    listOf(Sector.SingleInner17, Sector.Triple17, Sector.SingleOuter17, Sector.Double17),
    listOf(Sector.SingleInner3, Sector.Triple3, Sector.SingleOuter3, Sector.Double3),
    listOf(Sector.SingleInner19, Sector.Triple19, Sector.SingleOuter19, Sector.Double19),
    listOf(Sector.SingleInner7, Sector.Triple7, Sector.SingleOuter7, Sector.Double7),
    listOf(Sector.SingleInner16, Sector.Triple16, Sector.SingleOuter16, Sector.Double16),
    listOf(Sector.SingleInner8, Sector.Triple8, Sector.SingleOuter8, Sector.Double8),
    listOf(Sector.SingleInner11, Sector.Triple11, Sector.SingleOuter11, Sector.Double11),
    listOf(Sector.SingleInner14, Sector.Triple14, Sector.SingleOuter14, Sector.Double14),
    listOf(Sector.SingleInner9, Sector.Triple9, Sector.SingleOuter9, Sector.Double9),
    listOf(Sector.SingleInner12, Sector.Triple12, Sector.SingleOuter12, Sector.Double12),
    listOf(Sector.SingleInner5, Sector.Triple5, Sector.SingleOuter5, Sector.Double5),
    listOf(Sector.SingleInner20, Sector.Triple20, Sector.SingleOuter20, Sector.Double20),
    )

@Composable
fun GameInput(
    currentSet: Set,
    playerName: String,
    onClick: (Sector) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 64.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        CurrentSetItem(currentSet = currentSet, player = playerName)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sectors) {
                InputRow(sectors = it, onClick = onClick)
            }
        }
    }
}

@Composable
fun InputRow(sectors: List<Sector>, onClick: (Sector) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        sectors.forEach {
            InputCell(sector = it, modifier = Modifier.weight(1f)) {
                onClick(it)
            }
        }
    }
}

@Composable
fun InputCell(
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