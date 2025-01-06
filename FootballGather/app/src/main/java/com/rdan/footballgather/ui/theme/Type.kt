package com.rdan.footballgather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rdan.footballgather.R

val PromptFontFamily = FontFamily(
    Font(R.font.prompt_regular),
    Font(R.font.prompt_light),
    Font(R.font.prompt_medium),
    Font(R.font.prompt_bold),
)

val PTSerifFontFamily = FontFamily(
    Font(R.font.ptserif_regular),
    Font(R.font.ptserif_bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = PromptFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    displayMedium = TextStyle(
        fontFamily = PromptFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = PromptFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = PromptFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = PromptFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = PromptFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    titleLarge = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    titleMedium = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = PTSerifFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp
    ),
)