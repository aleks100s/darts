package com.alextos.darts.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alextos.darts.core.presentation.Colors

val AccentViolet = Color(Colors.AccentViolet)
val TextBlack = Color(Colors.TextBlack)
val DarkGrey = Color(Colors.DarkGrey)

val darkColors = darkColors(
    primary = AccentViolet,
    onPrimary = Color.White,
    background = TextBlack,
    onBackground = Color.White,
    surface = DarkGrey,
    onSurface = Color.White
)

val lightColors = lightColors(
    primary = AccentViolet,
    onPrimary = Color.White,
    background = Color(Colors.LightBackground),
    onBackground = TextBlack,
    surface = Color(Colors.LightSurface),
    onSurface = TextBlack
)