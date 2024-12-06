package com.example.lookinapp.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LookinAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkColorScheme()
        } else {
            lightColorScheme()
        },
        content = content
    )
}

@Composable
fun darkColorScheme() = MaterialTheme.colorScheme.copy(
    background = Color(0xFF474444),
    onBackground = Color.White
)

@Composable
fun lightColorScheme() = MaterialTheme.colorScheme.copy(
    background = Color.White,
    onBackground = Color.Black
)
