package com.alextos.darts.android.game.calculator

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
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
        Text("This is calculator")
    }
}