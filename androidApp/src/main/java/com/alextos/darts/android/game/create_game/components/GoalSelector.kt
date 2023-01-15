package com.alextos.darts.android.game.create_game.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GoalSelector(
    goals: List<Int>,
    selectedGoal: Int? = null,
    onClick: (Int) -> Unit
) {
    LazyRow(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        items(goals) {
            GoalOptionItem(
                goal = it,
                isSelected = selectedGoal == it
            ) {
                onClick(it)
            }
        }
    }
}