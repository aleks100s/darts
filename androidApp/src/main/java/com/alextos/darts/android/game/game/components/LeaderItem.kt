package com.alextos.darts.android.game.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.alextos.darts.android.R

@Composable
fun LeaderItem(score: Int) {
    Text(
        text = stringResource(id = R.string.leader_score, score),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    )
}