package com.alextos.darts.android.common.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun LinkText(
    text: String,
    style: TextStyle = MaterialTheme.typography.body1
) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colors.primary,
        textDecoration = TextDecoration.Underline,
        maxLines = 1
    )
}