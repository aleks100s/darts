package com.alextos.darts.android.game.create_game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Center
    )
}