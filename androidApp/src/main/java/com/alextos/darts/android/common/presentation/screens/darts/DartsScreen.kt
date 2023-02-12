package com.alextos.darts.android.common.presentation.screens.darts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.DartsSetView
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

    Screen(title = stringResource(id = R.string.view_turn)) {
        HorizontalPager(
            count = state.turns.count(),
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            DartsSetView(set = state.turns[page])
        }
    }
}