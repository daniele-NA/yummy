package me.app.yummy.compose.theme

import yummy.composeapp.generated.resources.Res
import yummy.composeapp.generated.resources.*
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

@Composable
fun getTypography(materialColorScheme: ColorScheme): Typography {

    //Composable function
    val title = Font(resource = Res.font.title)
    val body = Font(resource = Res.font.body)
    val letterSpacing = 0.5.sp
    val lineHeight = 25.sp

    return Typography(
        titleLarge = TextStyle(
            fontFamily = FontFamily(title), fontSize = h1,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        ),

        titleMedium = TextStyle(
            fontFamily = FontFamily(title), fontSize = h2,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        ),

        titleSmall = TextStyle(
            fontFamily = FontFamily(title), fontSize = h3,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        ),

        bodyLarge = TextStyle(
            fontFamily = FontFamily(body), fontSize = h3,
            letterSpacing = letterSpacing,
            color = materialColorScheme.onBackground,
            lineHeight = lineHeight
        ),

        bodyMedium = TextStyle(
            fontFamily = FontFamily(body), fontSize = h4,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
    )
}