package com.alextos.darts.android.game.darts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.components.DartsSetView
import com.alextos.darts.game.presentation.darts.DartsState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DartsScreen(state: DartsState) {
    val pagerState = rememberPagerState(initialPage = state.currentPage)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = state.currentPage) {
        scope.launch {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }

    HorizontalPager(
        count = state.turns.count(),
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 16.dp, end = 16.dp)
    ) { page ->
        DartsSetView(set = state.turns[page])
    }
}