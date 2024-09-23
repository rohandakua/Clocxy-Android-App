package com.example.clockappbyrohan.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.clockappbyrohan.R

val PrimaryColor = Color(0xFF000000)
val PrimaryVariantColor = Color(0xFF480853)
val SecondaryColor = Color(0xFF03DAC6)
val TextColor = Color(0xFFF2683C)

val CustomFontFamilyKanit = FontFamily(
    Font(R.font.kanit, FontWeight.Normal)
)
val CustomFontFamilyKarla = FontFamily(
    Font(R.font.karla, FontWeight.Normal)
)
val CustomFontFamilyPacifico = FontFamily(
    Font(R.font.pacifico, FontWeight.Normal)
)
val CustomFontFamilyInter = FontFamily(
    Font(R.font.inter, FontWeight.Normal)
)


val CustomTypographyKarla = Typography(
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = CustomFontFamilyKarla,
        color = TextColor
    )
)
val CustomTypographyKanit = Typography(
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = CustomFontFamilyKanit,
        color = TextColor
    )
)
val CustomTypographyPacifico = Typography(
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = CustomFontFamilyPacifico,
        color = TextColor
    )
)
val CustomTypographyInter = Typography(
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = CustomFontFamilyInter,
        color = TextColor
    )
)
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    primaryContainer = PrimaryVariantColor,
    secondary = SecondaryColor,
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = TextColor,
    surface = Color.White,
    onSurface = TextColor
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    primaryContainer = PrimaryVariantColor,
    secondary = SecondaryColor,
    onSecondary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.DarkGray,
    onSurface = Color.White
)


@Composable
fun CustomThemeKarla(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = CustomTypographyKarla,
        content = content
    )
}

@Composable
fun CustomThemeKanit(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = CustomTypographyKanit,
        content = content
    )
}

@Composable
fun CustomThemePacifico(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = CustomTypographyPacifico,
        content = content
    )
}

@Composable
fun CustomThemeInter(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = CustomTypographyInter,
        content = content
    )
}

