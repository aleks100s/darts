package com.alextos.darts.android.common.presentation.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun Modifier.rounded(): Modifier = composed {
    this.wrapContentHeight()
        .padding(horizontal = 16.dp)
        .padding(top = 16.dp, bottom = 72.dp)
        .background(MaterialTheme.colors.background)
        .clip(RoundedCornerShape(16.dp))
}

fun Modifier.surfaceBackground(): Modifier = composed {
    background(MaterialTheme.colors.surface)
}