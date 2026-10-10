package com.jeet_studio.localstream4k.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PeachAccent,
    secondary = PeachButton,
    background = DarkBackground,
    surface = SurfaceDark,
    onPrimary = DarkBackground,
    onBackground = TextLight,
    onSurface = TextLight
)

@Composable
fun LocalStreamTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
