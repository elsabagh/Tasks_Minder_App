package com.example.Tasks_Minder_App.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.Tasks_Minder_App.data.model.ThemeColor

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)

private val redLightScheme = lightColorScheme(
    primary = redPrimaryLight,
    onPrimary = redOnPrimaryLight,
    primaryContainer = redPrimaryContainerLight,
    onPrimaryContainer = redOnPrimaryContainerLight,
    secondary = redSecondaryLight,
    onSecondary = redOnSecondaryLight,
    secondaryContainer = redSecondaryContainerLight,
    onSecondaryContainer = redOnSecondaryContainerLight,
    tertiary = redTertiaryLight,
    onTertiary = redOnTertiaryLight,
    tertiaryContainer = redTertiaryContainerLight,
    onTertiaryContainer = redOnTertiaryContainerLight,
    error = redErrorLight,
    onError = redOnErrorLight,
    errorContainer = redErrorContainerLight,
    onErrorContainer = redOnErrorContainerLight,
    background = redBackgroundLight,
    onBackground = redOnBackgroundLight,
    surface = redSurfaceLight,
    onSurface = redOnSurfaceLight,
    surfaceVariant = redSurfaceVariantLight,
    onSurfaceVariant = redOnSurfaceVariantLight,
    outline = redOutlineLight,
    outlineVariant = redOutlineVariantLight,
    scrim = redScrimLight,
    inverseSurface = redInverseSurfaceLight,
    inverseOnSurface = redInverseOnSurfaceLight,
    inversePrimary = redInversePrimaryLight,
    surfaceDim = redSurfaceDimLight,
    surfaceBright = redSurfaceBrightLight,
    surfaceContainerLowest = redSurfaceContainerLowestLight,
    surfaceContainerLow = redSurfaceContainerLowLight,
    surfaceContainer = redSurfaceContainerLight,
    surfaceContainerHigh = redSurfaceContainerHighLight,
    surfaceContainerHighest = redSurfaceContainerHighestLight,
)

private val redDarkScheme = darkColorScheme(
    primary = redPrimaryDark,
    onPrimary = redOnPrimaryDark,
    primaryContainer = redPrimaryContainerDark,
    onPrimaryContainer = redOnPrimaryContainerDark,
    secondary = redSecondaryDark,
    onSecondary = redOnSecondaryDark,
    secondaryContainer = redSecondaryContainerDark,
    onSecondaryContainer = redOnSecondaryContainerDark,
    tertiary = redTertiaryDark,
    onTertiary = redOnTertiaryDark,
    tertiaryContainer = redTertiaryContainerDark,
    onTertiaryContainer = redOnTertiaryContainerDark,
    error = redErrorDark,
    onError = redOnErrorDark,
    errorContainer = redErrorContainerDark,
    onErrorContainer = redOnErrorContainerDark,
    background = redBackgroundDark,
    onBackground = redOnBackgroundDark,
    surface = redSurfaceDark,
    onSurface = redOnSurfaceDark,
    surfaceVariant = redSurfaceVariantDark,
    onSurfaceVariant = redOnSurfaceVariantDark,
    outline = redOutlineDark,
    outlineVariant = redOutlineVariantDark,
    scrim = redScrimDark,
    inverseSurface = redInverseSurfaceDark,
    inverseOnSurface = redInverseOnSurfaceDark,
    inversePrimary = redInversePrimaryDark,
    surfaceDim = redSurfaceDimDark,
    surfaceBright = redSurfaceBrightDark,
    surfaceContainerLowest = redSurfaceContainerLowestDark,
    surfaceContainerLow = redSurfaceContainerLowDark,
    surfaceContainer = redSurfaceContainerDark,
    surfaceContainerHigh = redSurfaceContainerHighDark,
    surfaceContainerHighest = redSurfaceContainerHighestDark,
)

private val greenLightScheme = lightColorScheme(
    primary = greenPrimaryLight,
    onPrimary = greenOnPrimaryLight,
    primaryContainer = greenPrimaryContainerLight,
    onPrimaryContainer = greenOnPrimaryContainerLight,
    secondary = greenSecondaryLight,
    onSecondary = greenOnSecondaryLight,
    secondaryContainer = greenSecondaryContainerLight,
    onSecondaryContainer = greenOnSecondaryContainerLight,
    tertiary = greenTertiaryLight,
    onTertiary = greenOnTertiaryLight,
    tertiaryContainer = greenTertiaryContainerLight,
    onTertiaryContainer = greenOnTertiaryContainerLight,
    error = greenErrorLight,
    onError = greenOnErrorLight,
    errorContainer = greenErrorContainerLight,
    onErrorContainer = greenOnErrorContainerLight,
    background = greenBackgroundLight,
    onBackground = greenOnBackgroundLight,
    surface = greenSurfaceLight,
    onSurface = greenOnSurfaceLight,
    surfaceVariant = greenSurfaceVariantLight,
    onSurfaceVariant = greenOnSurfaceVariantLight,
    outline = greenOutlineLight,
    outlineVariant = greenOutlineVariantLight,
    scrim = greenScrimLight,
    inverseSurface = greenInverseSurfaceLight,
    inverseOnSurface = greenInverseOnSurfaceLight,
    inversePrimary = greenInversePrimaryLight,
    surfaceDim = greenSurfaceDimLight,
    surfaceBright = greenSurfaceBrightLight,
    surfaceContainerLowest = greenSurfaceContainerLowestLight,
    surfaceContainerLow = greenSurfaceContainerLowLight,
    surfaceContainer = greenSurfaceContainerLight,
    surfaceContainerHigh = greenSurfaceContainerHighLight,
    surfaceContainerHighest = greenSurfaceContainerHighestLight,
)


private val greenDarkScheme = darkColorScheme(
    primary = greenPrimaryDark,
    onPrimary = greenOnPrimaryDark,
    primaryContainer = greenPrimaryContainerDark,
    onPrimaryContainer = greenOnPrimaryContainerDark,
    secondary = greenSecondaryDark,
    onSecondary = greenOnSecondaryDark,
    secondaryContainer = greenSecondaryContainerDark,
    onSecondaryContainer = greenOnSecondaryContainerDark,
    tertiary = greenTertiaryDark,
    onTertiary = greenOnTertiaryDark,
    tertiaryContainer = greenTertiaryContainerDark,
    onTertiaryContainer = greenOnTertiaryContainerDark,
    error = greenErrorDark,
    onError = greenOnErrorDark,
    errorContainer = greenErrorContainerDark,
    onErrorContainer = greenOnErrorContainerDark,
    background = greenBackgroundDark,
    onBackground = greenOnBackgroundDark,
    surface = greenSurfaceDark,
    onSurface = greenOnSurfaceDark,
    surfaceVariant = greenSurfaceVariantDark,
    onSurfaceVariant = greenOnSurfaceVariantDark,
    outline = greenOutlineDark,
    outlineVariant = greenOutlineVariantDark,
    scrim = greenScrimDark,
    inverseSurface = greenInverseSurfaceDark,
    inverseOnSurface = greenInverseOnSurfaceDark,
    inversePrimary = greenInversePrimaryDark,
    surfaceDim = greenSurfaceDimDark,
    surfaceBright = greenSurfaceBrightDark,
    surfaceContainerLowest = greenSurfaceContainerLowestDark,
    surfaceContainerLow = greenSurfaceContainerLowDark,
    surfaceContainer = greenSurfaceContainerDark,
    surfaceContainerHigh = greenSurfaceContainerHighDark,
    surfaceContainerHighest = greenSurfaceContainerHighestDark,
)

private val blueLightScheme = lightColorScheme(
    primary = bluePrimaryLight,
    onPrimary = blueOnPrimaryLight,
    primaryContainer = bluePrimaryContainerLight,
    onPrimaryContainer = blueOnPrimaryContainerLight,
    secondary = blueSecondaryLight,
    onSecondary = blueOnSecondaryLight,
    secondaryContainer = blueSecondaryContainerLight,
    onSecondaryContainer = blueOnSecondaryContainerLight,
    tertiary = blueTertiaryLight,
    onTertiary = blueOnTertiaryLight,
    tertiaryContainer = blueTertiaryContainerLight,
    onTertiaryContainer = blueOnTertiaryContainerLight,
    error = blueErrorLight,
    onError = blueOnErrorLight,
    errorContainer = blueErrorContainerLight,
    onErrorContainer = blueOnErrorContainerLight,
    background = blueBackgroundLight,
    onBackground = blueOnBackgroundLight,
    surface = blueSurfaceLight,
    onSurface = blueOnSurfaceLight,
    surfaceVariant = blueSurfaceVariantLight,
    onSurfaceVariant = blueOnSurfaceVariantLight,
    outline = blueOutlineLight,
    outlineVariant = blueOutlineVariantLight,
    scrim = blueScrimLight,
    inverseSurface = blueInverseSurfaceLight,
    inverseOnSurface = blueInverseOnSurfaceLight,
    inversePrimary = blueInversePrimaryLight,
    surfaceDim = blueSurfaceDimLight,
    surfaceBright = blueSurfaceBrightLight,
    surfaceContainerLowest = blueSurfaceContainerLowestLight,
    surfaceContainerLow = blueSurfaceContainerLowLight,
    surfaceContainer = blueSurfaceContainerLight,
    surfaceContainerHigh = blueSurfaceContainerHighLight,
    surfaceContainerHighest = blueSurfaceContainerHighestLight,
)

private val blueDarkScheme = darkColorScheme(
    primary = bluePrimaryDark,
    onPrimary = blueOnPrimaryDark,
    primaryContainer = bluePrimaryContainerDark,
    onPrimaryContainer = blueOnPrimaryContainerDark,
    secondary = blueSecondaryDark,
    onSecondary = blueOnSecondaryDark,
    secondaryContainer = blueSecondaryContainerDark,
    onSecondaryContainer = blueOnSecondaryContainerDark,
    tertiary = blueTertiaryDark,
    onTertiary = blueOnTertiaryDark,
    tertiaryContainer = blueTertiaryContainerDark,
    onTertiaryContainer = blueOnTertiaryContainerDark,
    error = blueErrorDark,
    onError = blueOnErrorDark,
    errorContainer = blueErrorContainerDark,
    onErrorContainer = blueOnErrorContainerDark,
    background = blueBackgroundDark,
    onBackground = blueOnBackgroundDark,
    surface = blueSurfaceDark,
    onSurface = blueOnSurfaceDark,
    surfaceVariant = blueSurfaceVariantDark,
    onSurfaceVariant = blueOnSurfaceVariantDark,
    outline = blueOutlineDark,
    outlineVariant = blueOutlineVariantDark,
    scrim = blueScrimDark,
    inverseSurface = blueInverseSurfaceDark,
    inverseOnSurface = blueInverseOnSurfaceDark,
    inversePrimary = blueInversePrimaryDark,
    surfaceDim = blueSurfaceDimDark,
    surfaceBright = blueSurfaceBrightDark,
    surfaceContainerLowest = blueSurfaceContainerLowestDark,
    surfaceContainerLow = blueSurfaceContainerLowDark,
    surfaceContainer = blueSurfaceContainerDark,
    surfaceContainerHigh = blueSurfaceContainerHighDark,
    surfaceContainerHighest = blueSurfaceContainerHighestDark,
)


private val purpleLightScheme = lightColorScheme(
    primary = purplePrimaryLight,
    onPrimary = purpleOnPrimaryLight,
    primaryContainer = purplePrimaryContainerLight,
    onPrimaryContainer = purpleOnPrimaryContainerLight,
    secondary = purpleSecondaryLight,
    onSecondary = purpleOnSecondaryLight,
    secondaryContainer = purpleSecondaryContainerLight,
    onSecondaryContainer = purpleOnSecondaryContainerLight,
    tertiary = purpleTertiaryLight,
    onTertiary = purpleOnTertiaryLight,
    tertiaryContainer = purpleTertiaryContainerLight,
    onTertiaryContainer = purpleOnTertiaryContainerLight,
    error = purpleErrorLight,
    onError = purpleOnErrorLight,
    errorContainer = purpleErrorContainerLight,
    onErrorContainer = purpleOnErrorContainerLight,
    background = purpleBackgroundLight,
    onBackground = purpleOnBackgroundLight,
    surface = purpleSurfaceLight,
    onSurface = purpleOnSurfaceLight,
    surfaceVariant = purpleSurfaceVariantLight,
    onSurfaceVariant = purpleOnSurfaceVariantLight,
    outline = purpleOutlineLight,
    outlineVariant = purpleOutlineVariantLight,
    scrim = purpleScrimLight,
    inverseSurface = purpleInverseSurfaceLight,
    inverseOnSurface = purpleInverseOnSurfaceLight,
    inversePrimary = purpleInversePrimaryLight,
    surfaceDim = purpleSurfaceDimLight,
    surfaceBright = purpleSurfaceBrightLight,
    surfaceContainerLowest = purpleSurfaceContainerLowestLight,
    surfaceContainerLow = purpleSurfaceContainerLowLight,
    surfaceContainer = purpleSurfaceContainerLight,
    surfaceContainerHigh = purpleSurfaceContainerHighLight,
    surfaceContainerHighest = purpleSurfaceContainerHighestLight,
)

private val purpleDarkScheme = darkColorScheme(
    primary = purplePrimaryDark,
    onPrimary = purpleOnPrimaryDark,
    primaryContainer = purplePrimaryContainerDark,
    onPrimaryContainer = purpleOnPrimaryContainerDark,
    secondary = purpleSecondaryDark,
    onSecondary = purpleOnSecondaryDark,
    secondaryContainer = purpleSecondaryContainerDark,
    onSecondaryContainer = purpleOnSecondaryContainerDark,
    tertiary = purpleTertiaryDark,
    onTertiary = purpleOnTertiaryDark,
    tertiaryContainer = purpleTertiaryContainerDark,
    onTertiaryContainer = purpleOnTertiaryContainerDark,
    error = purpleErrorDark,
    onError = purpleOnErrorDark,
    errorContainer = purpleErrorContainerDark,
    onErrorContainer = purpleOnErrorContainerDark,
    background = purpleBackgroundDark,
    onBackground = purpleOnBackgroundDark,
    surface = purpleSurfaceDark,
    onSurface = purpleOnSurfaceDark,
    surfaceVariant = purpleSurfaceVariantDark,
    onSurfaceVariant = purpleOnSurfaceVariantDark,
    outline = purpleOutlineDark,
    outlineVariant = purpleOutlineVariantDark,
    scrim = purpleScrimDark,
    inverseSurface = purpleInverseSurfaceDark,
    inverseOnSurface = purpleInverseOnSurfaceDark,
    inversePrimary = purpleInversePrimaryDark,
    surfaceDim = purpleSurfaceDimDark,
    surfaceBright = purpleSurfaceBrightDark,
    surfaceContainerLowest = purpleSurfaceContainerLowestDark,
    surfaceContainerLow = purpleSurfaceContainerLowDark,
    surfaceContainer = purpleSurfaceContainerDark,
    surfaceContainerHigh = purpleSurfaceContainerHighDark,
    surfaceContainerHighest = purpleSurfaceContainerHighestDark,
)
@Composable
fun TaskMinderTheme(
    themeColor: ThemeColor = ThemeColor.RED,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
    val lightScheme = when (themeColor) {
        ThemeColor.RED -> redLightScheme
        ThemeColor.GREEN -> greenLightScheme
        ThemeColor.BLUE -> blueLightScheme
        ThemeColor.PURPLE -> purpleLightScheme
    }

    val darkScheme = when (themeColor) {
        ThemeColor.RED -> redDarkScheme
        ThemeColor.GREEN -> greenDarkScheme
        ThemeColor.BLUE -> blueDarkScheme
        ThemeColor.PURPLE -> purpleDarkScheme
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}