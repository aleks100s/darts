package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R

@Composable
fun InputHintRow() {
    val strings = listOf(
        stringResource(id = R.string.single),
        stringResource(id = R.string.double_),
        stringResource(id = R.string.triplet)
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        for (string in strings) {
            Cell(modifier = Modifier.weight(1f), text = string)
        }
    }
}