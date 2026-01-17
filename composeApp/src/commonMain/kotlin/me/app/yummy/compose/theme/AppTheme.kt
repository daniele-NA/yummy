package me.app.yummy.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    // == It must be initialized first to avoid color problems in Type.kt == //
    val customColorScheme = getColorScheme(isSystemInDarkTheme())

    MaterialTheme(
        colorScheme = customColorScheme,
        typography = getTypography(customColorScheme),
        content = content
    )
}