package com.alextos.darts.android.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun rememberScreenType(): ScreenType {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp > 840 && configuration.screenHeightDp > 600 -> ScreenType.Large
        else -> ScreenType.Compact
    }
}

sealed class ScreenType {
    object Compact: ScreenType()
    object Large: ScreenType()
}