package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.model.Turn
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GameHistoryView(
    modifier: Modifier,
    gameHistory: List<PlayerHistory>,
    goal: Int,
    currentPage: Int,
    onSelect: (List<Turn>, Int) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = currentPage)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = currentPage) {
        scope.launch {
            pagerState.animateScrollToPage(currentPage)
        }
    }

    HorizontalPager(
        count = gameHistory.count(),
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        PlayerHistoryView(
            playerHistory = gameHistory[page],
            goal = goal
        ) {
            val turns = gameHistory[page].turns
            val turnPage = turns.indexOf(it)
            onSelect(gameHistory[page].turns, turnPage)
        }
    }
}