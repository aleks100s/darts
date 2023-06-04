package com.alextos.darts.android.common.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable

@Composable
fun Chevron(contentDescription: String) {
    Icon(
        imageVector = Icons.Filled.ChevronRight,
        contentDescription = contentDescription,
        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    )
}