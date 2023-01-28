package com.alextos.darts.android.common.presentation

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FAB(
    text: String,
    icon: ImageVector,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = {
            if (isEnabled) {
                onClick()
            }
        },
        icon = {
            Icon(
                icon,
                contentDescription = text
            )
        },
        text = { Text(text = text) },
        backgroundColor = if (isEnabled) {
            MaterialTheme.colors.secondary
        } else {
            MaterialTheme.colors.secondary.copy(alpha = 0.5f)
        },
        contentColor = if (isEnabled) {
            MaterialTheme.colors.onSecondary
        } else {
           Color.DarkGray
        }
    )
}