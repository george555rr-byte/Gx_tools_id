package com.gx.tools.id.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val GXColors = darkColorScheme(
    primary = NeonPurple,
    onPrimary = TextWhite,
    secondary = NeonPurple2,
    onSecondary = TextWhite,
    background = BgDark,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    outline = BorderPurple
)

@Composable
fun GXTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GXColors,
        content = content
    )
}
