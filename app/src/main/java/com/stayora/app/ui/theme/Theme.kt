package com.stayora.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = BackgroundWhite,
    primaryContainer = PurpleTint,
    onPrimaryContainer = PurpleDark,
    secondary = PurpleAccent,
    onSecondary = BackgroundWhite,
    secondaryContainer = PurpleSurface,
    onSecondaryContainer = PurpleDark,
    background = BackgroundWhite,
    onBackground = CharcoalDark,
    surface = BackgroundWhite,
    onSurface = CharcoalDark,
    surfaceVariant = PurpleCardBg,
    onSurfaceVariant = CharcoalMedium,
    outline = BorderSubtle
)

private val DarkColorScheme = darkColorScheme(
    primary = PurpleLight,
    onPrimary = BackgroundWhite,
    primaryContainer = PurpleDark,
    onPrimaryContainer = PurpleTint,
    secondary = PurpleSoft,
    onSecondary = BackgroundWhite,
    background = CharcoalDark,
    onBackground = BackgroundWhite,
    surface = Color(0xFF261F53),
    onSurface = BackgroundWhite,
    surfaceVariant = Color(0xFF31286B),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

@Composable
fun StayoraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                it.statusBarColor = PurplePrimary.toArgb()
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
