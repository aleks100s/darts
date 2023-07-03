package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(
    title: String,
    defaultActionTitle: String,
    defaultAction: () -> Unit,
    destructiveActionTitle: String,
    destructiveAction: () -> Unit,
    cancelActionTitle: String,
    cancelAction: () -> Unit
) {
    Dialog(onDismissRequest = cancelAction) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomDialogButton(
                title = defaultActionTitle,
                onClick = defaultAction
            )

            CustomDialogButton(
                title = destructiveActionTitle,
                color = MaterialTheme.colors.error,
                onClick = destructiveAction
            )

            CustomDialogButton(
                title = cancelActionTitle,
                fontWeight = FontWeight.Bold,
                onClick =  cancelAction
            )
        }
    }
}

@Composable
private fun CustomDialogButton(
    title: String,
    color: Color = MaterialTheme.colors.primary,
    fontWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit
) {
    Column {
        Divider()

        Text(
            text = title,
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = fontWeight
        )
    }
}