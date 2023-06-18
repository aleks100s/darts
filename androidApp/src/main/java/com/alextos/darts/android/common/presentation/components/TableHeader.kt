package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground

@Composable
fun TableHeader(columns: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .surfaceBackground()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        columns.forEach {
            Text(
                modifier = Modifier.weight(1f),
                text = it,
                textAlign = TextAlign.Center
            )
        }
    }
}