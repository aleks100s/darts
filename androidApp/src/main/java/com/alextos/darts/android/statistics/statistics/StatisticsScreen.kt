package com.alextos.darts.android.statistics.statistics

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.SingleSelectableItem
import com.alextos.darts.statistics.presentation.statistics.StatisticsEvent

@Composable
fun StatisticsScreen(onEvent: (StatisticsEvent) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(top = 32.dp)
    ) {
        item {
            SingleSelectableItem(text = stringResource(id = R.string.best_set_statistics)) {
                onEvent(StatisticsEvent.ShowBestSet)
            }
        }
    }
}