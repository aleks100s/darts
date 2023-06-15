package com.alextos.darts.android.game.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.components.FAB
import com.alextos.darts.android.common.presentation.components.InputHintRow
import com.alextos.darts.android.common.presentation.components.InputMatrix
import com.alextos.darts.android.common.presentation.components.TurnItem
import com.alextos.darts.android.common.presentation.extensions.surfaceBackground
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.game.presentation.calculator.CalculatorEvent
import com.alextos.darts.game.presentation.calculator.CalculatorState

@Composable
fun CalculatorScreen(
    state: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit
) {
    Screen(
        title = stringResource(id = R.string.calculator),
        onBackPressed = { onEvent(CalculatorEvent.BackButtonPressed) }
    ) {
        Scaffold(
            floatingActionButton = {
                FAB(
                    text = stringResource(id = R.string.erase_hit),
                    icon = Icons.Default.Delete
                ) {
                    onEvent(CalculatorEvent.UndoLastShot)
                }
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .surfaceBackground()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.game_player_result, state.score),
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        text = stringResource(id = R.string.turn_number, state.turnNumber),
                        style = MaterialTheme.typography.caption
                    )
                }
                TurnItem(
                    turn = state.lastTurn,
                    useTurnColors = false,
                    onSelect = {}
                )
                InputHintRow()
                InputMatrix { sector ->
                    onEvent(CalculatorEvent.MakeShot(sector))
                }
            }
        }
    }
}