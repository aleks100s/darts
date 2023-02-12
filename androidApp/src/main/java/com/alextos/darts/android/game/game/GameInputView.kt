package com.alextos.darts.android.game.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.*
import com.alextos.darts.android.common.presentation.extensions.color
import com.alextos.darts.android.common.presentation.extensions.textColor
import com.alextos.darts.core.domain.Sector
import com.alextos.darts.core.domain.Set
import com.alextos.darts.game.domain.models.GamePlayerResult

@Composable
fun GameInputView(
    currentSet: Set,
    results: List<GamePlayerResult>,
    eraseShot: () -> Unit,
    onClick: (Sector) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentTurnItem(currentSet = currentSet, results = results)
            HintRow()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(Sector.sectors) {
                    InputRow(sectors = it, onClick = onClick)
                }
                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }

        Button(
            onClick = { eraseShot() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(id = R.string.erase_hit))
        }
    }
}

@Composable
private fun CurrentTurnItem(
    results: List<GamePlayerResult>,
    currentSet: Set
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GamePlayers(list = results)
        PlayerHistoryHeader()
        SetItem(set = currentSet, onSelect = {})
    }
}

@Composable
private fun GamePlayers(list: List<GamePlayerResult>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = if (list.count() < 4) {
        screenWidth / list.count()
    } else {
        screenWidth / 3
    }
    LazyRow {
        items(list) { result ->
            GamePlayerItem(result = result, width = itemWidth)
        }
    }
}

@Composable
private fun GamePlayerItem(result: GamePlayerResult, width: Dp) {
    val backgroundColor = if (result.isCurrentPlayer) {
        MaterialTheme.colors.secondary
    } else {
        MaterialTheme.colors.surface
    }
    val textColor = if (result.isCurrentPlayer) {
        MaterialTheme.colors.onSecondary
    } else {
        MaterialTheme.colors.onSurface
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(backgroundColor)
            .width(width)
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = result.player.name,
            color = textColor,
            style = MaterialTheme.typography.h3
        )
        Text(
            text = stringResource(id = R.string.game_player_result, result.result),
            color = textColor
        )
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