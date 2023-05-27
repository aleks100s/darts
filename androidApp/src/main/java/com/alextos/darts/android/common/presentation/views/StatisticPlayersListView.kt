package com.alextos.darts.android.common.presentation.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alextos.darts.android.common.presentation.components.PlayerDisclosureItem
import com.alextos.darts.android.common.presentation.components.RoundedView
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground
import com.alextos.darts.core.domain.model.Player

@Composable
fun StatisticPlayersListView(
    modifier: Modifier,
    values: List<Pair<Player, String>>,
    onValueClick: (Int) -> Unit
) {
    if (values.isEmpty()) {
        NoDataView(modifier)
    } else {
        RoundedView(modifier) {
            LazyColumn(modifier = Modifier.surfaceBackground()) {
                items(values) { value ->
                    PlayerDisclosureItem(
                        player = value.first,
                        rightText = value.second,
                        showDivider = values.last() != value
                    ) {
                        onValueClick(values.indexOf(value))
                    }
                }
            }
        }
    }
}