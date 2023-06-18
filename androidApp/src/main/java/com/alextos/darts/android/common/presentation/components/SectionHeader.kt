package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .surfaceBackground()
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h3
    )
}