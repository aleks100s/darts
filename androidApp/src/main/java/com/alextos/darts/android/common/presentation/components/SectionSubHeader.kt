package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground

@Composable
fun SectionSubHeader(subtitle: String) {
    Text(
        text = subtitle,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .surfaceBackground()
    )
}