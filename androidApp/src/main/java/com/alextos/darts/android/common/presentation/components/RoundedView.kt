package com.alextos.darts.android.common.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alextos.darts.android.common.presentation.extensions.rounded

@Composable
fun RoundedView(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .rounded()
    ) {
        content()
    }
}