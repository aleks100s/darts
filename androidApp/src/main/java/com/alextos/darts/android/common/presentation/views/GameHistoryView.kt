package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.core.domain.Set
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GameHistoryView(
    gameHistory: List<PlayerHistory>,
    goal: Int,
    currentPage: Int,
    padding: PaddingValues,
    onSelect: (List<Set>, Set) -> Unit
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
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) { page ->
        PlayerHistoryView(
            playerHistory = gameHistory[page],
            goal = goal
        ) {
            onSelect(gameHistory[page].turns, it)
        }
    }
}