package com.alextos.darts.android.game.create_player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.game.presentation.create_player.CreatePlayerEvent
import com.alextos.darts.game.presentation.create_player.CreatePlayerState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreatePlayerScreen(
    state: CreatePlayerState,
    onEvent: (CreatePlayerEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = stringResource(id = R.string.enter_new_player_name))

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = state.name,
            onValueChange = { onEvent(CreatePlayerEvent.ChangeNewPlayerName(it)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onEvent(CreatePlayerEvent.SavePlayer(state.name))
                keyboardController?.hide()
            })
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onEvent(CreatePlayerEvent.SavePlayer(state.name))
            },
            enabled = state.isNameValid()
        ) {
            Text(text = stringResource(id = R.string.create_player))
        }
    }
}