package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Cell(
    modifier: Modifier,
    text: String,
    textColor: Color = Color.Unspecified
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Center,
        color = textColor
    )
}