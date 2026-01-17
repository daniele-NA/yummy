package me.app.yummy.compose.theme

import androidx.compose.material3.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val primary = Color(0xFF1A1A1A)
internal val textBrush = Brush.horizontalGradient(
    colors = listOf(Color.Yellow, Color.Red)
)

internal val buttonBrush = listOf(
    Color.Red,
    Color(0xFFFFA500),
    Color.Transparent
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFFFA07A),
    onSecondary = Color(0xFF4A1C00),
    tertiary = Color(0xFFFFC1CC),
    onTertiary = Color(0xFF4A1F2A),
    background = Color(0xFFFFF6EB),
    onBackground = Color(0xFF2B2B2B),
    surface = Color(0xFFF8F4EE),
    onSurface = Color(0xFF2B2B2B),
    error = Color(0xFFE8A1A1),
    onError = Color(0xFF4A0000)
)

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFFF7F50),
    onSecondary = Color(0xFF2B0F00),
    tertiary = Color(0xFFE3A0AD),
    onTertiary = Color(0xFF3A1420),
    background = Color(0xFF2B2B2B),
    onBackground = Color(0xFFE6E6E6),
    surface = Color(0xFF2F2F2F),
    onSurface = Color(0xFFE6E6E6),
    error = Color(0xFFCC7A7A),
    onError = Color(0xFF330000)
)

internal fun getColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) DarkColorScheme else LightColorScheme
}
