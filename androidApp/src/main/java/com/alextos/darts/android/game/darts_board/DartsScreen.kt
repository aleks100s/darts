package com.alextos.darts.android.game.darts_board

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.DartsSetView
import com.alextos.darts.core.domain.model.Shot
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DartsScreen(
    turns: List<List<Shot>>,
    currentPage: Int
) {
    val pagerState = rememberPagerState(initialPage = currentPage)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = currentPage) {
        scope.launch {
            pagerState.animateScrollToPage(currentPage)
        }
    }

    Screen(title = stringResource(id = R.string.view_turn)) {
        HorizontalPager(
            count = turns.count(),
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            DartsSetView(set = turns[page])
        }
    }
}