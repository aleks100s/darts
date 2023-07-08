package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.ScreenType
import com.alextos.darts.android.common.presentation.components.*
import com.alextos.darts.android.common.presentation.rememberScreenType
import com.alextos.darts.core.domain.model.Sector
import com.alextos.darts.core.domain.model.Turn
import com.alextos.darts.game.domain.models.GamePlayerResult

@Composable
fun GameInputView(
    modifier: Modifier,
    currentTurn: Turn,
    results: List<GamePlayerResult>,
    currentPlayerIndex: Int,
    isStatisticsEnabled: Boolean,
    eraseShot: () -> Unit,
    onInputClick: (Sector) -> Unit,
    onPlayerClick: (Int) -> Unit
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
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CurrentTurnItem(
                    currentTurn = currentTurn,
                    results = results,
                    currentPlayerIndex = currentPlayerIndex,
                    isStatisticsEnabled = isStatisticsEnabled,
                    onClick = onPlayerClick
                )
                InputHintRow()
                InputMatrix(onInputClick = onInputClick)
            }
        }
    }
}

@Composable
private fun CurrentTurnItem(
    results: List<GamePlayerResult>,
    currentTurn: Turn,
    currentPlayerIndex: Int,
    isStatisticsEnabled: Boolean,
    onClick: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GamePlayers(list = results, currentPlayerIndex, isStatisticsEnabled, onClick)
        PlayerHistoryHeader()
        TurnItem(turn = currentTurn, useTurnColors = true, onSelect = {})
    }
}

@Composable
private fun GamePlayers(
    list: List<GamePlayerResult>,
    currentPlayerIndex: Int,
    isStatisticsEnabled: Boolean,
    onClick: (Int) -> Unit
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
            GamePlayerItem(
                result = result,
                isStatisticsEnabled = isStatisticsEnabled,
                width = itemWidth,
                onClick = { onClick(list.indexOf(result)) }
            )
        }
    }
}

@Composable
private fun GamePlayerItem(
    result: GamePlayerResult,
    isStatisticsEnabled: Boolean,
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
            .padding(vertical = 8.dp)
    ) {
        LinkText(text = result.player.name, style = MaterialTheme.typography.h3)
        Text(
            text = stringResource(id = R.string.game_player_result, result.score),
            color = textColor
        )
        if (isStatisticsEnabled) {
            Text(
                text = stringResource(
                    id = R.string.game_player_avg,
                    result.turnAverage,
                    result.overallTurnAverage
                ),
                style = MaterialTheme.typography.caption,
                color = textColor
            )
        }
    }
}