package com.alextos.darts.android.game.calculator_history

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alextos.darts.android.R
import com.alextos.darts.android.common.presentation.screens.Screen
import com.alextos.darts.android.common.presentation.views.TurnsHistoryView
import com.alextos.darts.core.domain.model.Turn

@Composable
fun CalculatorHistoryScreen(
    turns: List<Turn>,
    onSelect: (Turn) -> Unit,
    onBackPressed: () -> Unit
) {
    Screen(title = stringResource(id = R.string.history), onBackPressed = onBackPressed) {
        TurnsHistoryView(
            turns = turns,
            useTurnColors = false,
            onSelect = onSelect
        )
    }
}