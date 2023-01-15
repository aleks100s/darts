package com.alextos.darts.android.game.game.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.alextos.darts.android.common.presentation.FAB
import com.alextos.darts.game.domain.models.PlayerHistory
import com.alextos.darts.game.domain.models.Sector
import com.alextos.darts.game.domain.models.Shot
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GameHistoryScreen(
    gameHistory: List<PlayerHistory>,
    currentPage: Int,
    onShot: (Sector) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = currentPage)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = currentPage) {
        scope.launch {
            pagerState.animateScrollToPage(currentPage)
        }
    }

    Scaffold(
        floatingActionButton = {
            FAB(text = "Shot", icon = Icons.Filled.Dashboard) {
                onShot(Sector.Double11)
            }
        }
    ) {
        HorizontalPager(
            count = gameHistory.count(),
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) { page ->
            PlayerHistoryScreen(playerHistory = gameHistory[page])
        }
    }
}