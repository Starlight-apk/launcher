package com.pkgbash.launcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.pkgbash.launcher.ui.theme.*

@Composable
fun GlassBackground(
    modifier: Modifier = Modifier,
    blurRadius: Int = 20,
    alpha: Float = 0.8f,
    enableBorder: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .blur(blurRadius.dp)
            .alpha(alpha)
            .background(
                Brush.verticalGradient(
                    colors = listOf(GlassBackground, GlassBackground.copy(alpha = 0.5f))
                )
            )
    )
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Surface.copy(alpha = 0.8f),
                        SurfaceVariant.copy(alpha = 0.6f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(GlassBorder, GlassBorder.copy(alpha = 0.3f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        content()
    }
}

@Composable
fun DynamicGradientBackground(
    modifier: Modifier = Modifier,
    isDark: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    colors = if (isDark) {
                        listOf(Background, BackgroundSecondary, Background)
                    } else {
                        listOf(Color(0xFFF5F7FA), Color(0xFFE8EBF0), Color(0xFFF5F7FA))
                    }
                )
            )
    )
}
