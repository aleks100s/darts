package com.alextos.darts.android.game.game_list.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alextos.darts.game.domain.models.Game

@Composable
fun GameItem(
    game: Game,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
            .clickable { onClick() }
    ) {
        Column {
            Text(text = "Game")
            Text(text = "12.12.22")
        }
    }
}