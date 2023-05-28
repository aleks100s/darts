package com.alextos.darts.android

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.alextos.darts.core.presentation.Colors

val AccentBlue = Color(Colors.AccentBlue)
val SecondaryGreen = Color(Colors.SecondaryGreen)
val TextBlack = Color(Colors.TextBlack)
val DarkGrey = Color(Colors.DarkGrey)

val darkColors = darkColors(
    primary = AccentBlue,
    onPrimary = Color.White,
    secondary = SecondaryGreen,
    onSecondary = Color.Black,
    background = TextBlack,
    onBackground = Color.White,
    surface = DarkGrey,
    onSurface = Color.White
)

val lightColors = lightColors(
    primary = AccentBlue,
    onPrimary = Color.White,
    secondary = SecondaryGreen,
    onSecondary = Color.Black,
    background = Color(Colors.LightBackground),
    onBackground = TextBlack,
    surface = Color(Colors.LightSurface),
    onSurface = TextBlack
)