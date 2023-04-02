package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.ScreenType
import com.alextos.darts.android.common.presentation.components.*
import com.alextos.darts.android.common.presentation.extensions.color
import com.alextos.darts.android.common.presentation.extensions.inputString
import com.alextos.darts.android.common.presentation.extensions.textColor
import com.alextos.darts.android.common.presentation.rememberScreenType
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Set
import com.alextos.darts.game.domain.models.GamePlayerResult

@Composable
fun GameInputView(
    currentSet: Set,
    results: List<GamePlayerResult>,
    currentPlayerIndex: Int,
    eraseShot: () -> Unit,
    onInputClick: (Sector) -> Unit,
    onPlayerClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FAB(
                text = stringResource(id = R.string.erase_hit),
                icon = Icons.Default.Delete
            ) {
                eraseShot()
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CurrentTurnItem(
                    currentSet = currentSet,
                    results = results,
                    currentPlayerIndex = currentPlayerIndex,
                    onClick = onPlayerClick
                )
                HintRow()
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
        }
    }
}

@Composable
private fun CurrentTurnItem(
    results: List<GamePlayerResult>,
    currentSet: Set,
    currentPlayerIndex: Int,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GamePlayers(list = results, currentPlayerIndex, onClick)
        PlayerHistoryHeader()
        SetItem(set = currentSet, onSelect = {})
    }
}

@Composable
private fun GamePlayers(
    list: List<GamePlayerResult>,
    currentPlayerIndex: Int,
    onClick: () -> Unit
) {
    val screenWidth = when (rememberScreenType()) {
        is ScreenType.Compact -> {
            LocalConfiguration.current.screenWidthDp.dp
        }
        is ScreenType.Large -> {
            LocalConfiguration.current.screenWidthDp.dp / 2
        }
    }
    val itemWidth = if (list.count() < 4) {
        screenWidth / list.count()
    } else {
        screenWidth / 3
    }
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = currentPlayerIndex) {
        listState.animateScrollToItem(index = currentPlayerIndex)
    }
    LazyRow(
        state = listState
    ) {
        items(list) { result ->
            GamePlayerItem(result = result, width = itemWidth, onClick = onClick)
        }
    }
}

@Composable
private fun GamePlayerItem(
    result: GamePlayerResult,
    width: Dp,
    onClick: () -> Unit
) {
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
            .clickable { onClick() }
            .width(width)
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = result.player.name,
            color = textColor,
            style = MaterialTheme.typography.h3,
            maxLines = 1
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
        stringResource(id = R.string.single),
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