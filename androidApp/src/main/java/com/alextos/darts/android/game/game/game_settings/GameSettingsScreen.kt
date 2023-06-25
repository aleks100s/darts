package com.alextos.darts.android.game.game.game_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.game.domain.models.GameSettings

@Composable
fun GameSettingsScreen(
    gameSettings: GameSettings,
    onClose: () -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Title()
        Settings(gameSettings = gameSettings)
        CloseButton(onClose = onClose)
    }
}

@Composable
private fun Title() {
    Text(
        modifier = Modifier.padding(16.dp),
        text = stringResource(id = R.string.game_settings),
        style = MaterialTheme.typography.h3
    )
}

@Composable
private fun Settings(gameSettings: GameSettings) {
    val yes = "✅"
    val no = "❌"
    LabeledText(
        label = stringResource(id = R.string.finish_with_doubles),
        text = if (gameSettings.isFinishWithDoublesChecked) yes else no
    )
    LabeledText(
        label = stringResource(id = R.string.turn_limit),
        text = if (gameSettings.isTurnLimitEnabled) yes else no
    )
    LabeledText(
        label = stringResource(id = R.string.randomize_player_order),
        text = if (gameSettings.isRandomPlayersOrderChecked) yes else no
    )
    LabeledText(
        label = stringResource(id = R.string.enable_statistics),
        text = if (gameSettings.isStatisticsEnabled) yes else no
    )
}

@Composable
private fun LabeledText(label: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = text)
    }
}

@Composable
private fun CloseButton(onClose: () -> Unit) {
    Button(
        modifier = Modifier.padding(16.dp),
        onClick = onClose
    ) {
        Text(text = stringResource(id = R.string.return_to_game))
    }
}