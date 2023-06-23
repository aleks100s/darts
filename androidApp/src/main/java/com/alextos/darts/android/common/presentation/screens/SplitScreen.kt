package com.alextos.darts.android.common.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SplitScreen(
    title: String,
    isBackButtonVisible: Boolean = true,
    content1: @Composable () -> Unit,
    content2: @Composable () -> Unit,
    additionalNavBarContent: @Composable (() -> Unit)? = null,
    onBackPressed: () -> Unit
) {
    Screen(
        title = title,
        isBackButtonVisible = isBackButtonVisible,
        onBackPressed = onBackPressed,
        additionalNavBarContent = additionalNavBarContent
    ) { modifier ->
        Row(modifier = modifier.fillMaxSize()) {
            Box(Modifier.weight(1f)) {
                content1()
            }

            Spacer(modifier = Modifier.width(2.dp))

            Box(Modifier.weight(1f)) {
                content2()
            }
        }
    }
}