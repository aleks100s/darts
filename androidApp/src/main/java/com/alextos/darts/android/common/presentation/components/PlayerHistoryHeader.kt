package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground

@Composable
fun PlayerHistoryHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .surfaceBackground()
            .padding(16.dp)
    ) {
        Cell(modifier = Modifier.weight(1f), text = stringResource(id = R.string.shot1))
        Cell(modifier = Modifier.weight(1f), text = stringResource(id = R.string.shot2))
        Cell(modifier = Modifier.weight(1f), text = stringResource(id = R.string.shot3))
        Cell(modifier = Modifier.weight(1f), text = stringResource(id = R.string.shot_sum))
        Cell(modifier = Modifier.weight(1f), text = stringResource(id = R.string.reminder))
    }
}