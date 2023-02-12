package com.alextos.darts.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.alextos.darts.android.navigation.BottomNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors
    } else {
        lightColors
    }

    val typography = Typography(
        h1 = TextStyle(
            fontSize = 30.sp
        ),
        h2 = TextStyle(
            fontSize = 24.sp
        ),
        h3 = TextStyle(
            fontSize = 18.sp
        ),
        body1 = TextStyle(
            fontSize = 14.sp
        ),
        body2 = TextStyle(
            fontSize = 12.sp
        ),
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ApplicationRoot()
                }
            }
        }
    }
}

@Composable
fun ApplicationRoot() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { com.alextos.darts.android.navigation.BottomNavigation(navController = navController) }
    ) {
        BottomNavigationGraph(navController = navController, it)
    }
}