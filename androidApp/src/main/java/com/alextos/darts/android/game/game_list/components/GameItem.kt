package com.alextos.darts.android.game.game_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.common.presentation.getTitle
import com.alextos.darts.game.domain.models.Game

@Composable
fun GameItem(
    game: Game,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = game.getTitle())
            Text(text = game.getDateString())
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            game.players.forEach { player ->
                PlayerCircle(
                    text = player.initials(),
                    isWinner = game.winner == player
                )
            }
        }
    }
}

@Composable
fun PlayerCircle(text: String, isWinner: Boolean) {
    val regularColor = MaterialTheme.colors.primary
    val winnerColor = MaterialTheme.colors.secondary
    Text(
        text = text,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .padding(if (isWinner) 4.dp else 2.dp)
            .drawBehind {
                drawCircle(
                    color = if (isWinner) winnerColor else regularColor,
                    radius = this.size.maxDimension
                )
            }
    )
}