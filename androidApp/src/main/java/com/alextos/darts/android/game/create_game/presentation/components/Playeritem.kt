package com.alextos.darts.android.game.create_game.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import com.alextos.darts.players.domain.models.Player

@Composable
fun PlayerItem(
    player: Player,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val color = MaterialTheme.colors.primary
        Text(
            text = player.initials(),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .padding(16.dp)
                .drawBehind {
                    drawCircle(
                        color = color,
                        radius = this.size.maxDimension
                    )
                }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = player.name,
            modifier = Modifier.fillMaxWidth(1F),
            style = MaterialTheme.typography.body1
        )
    }
}