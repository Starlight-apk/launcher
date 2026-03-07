package com.pkgbash.launcher.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

/**
 * 简化动画系统 - 只保留基本位移和淡入效果
 */

// 滑动 + 淡入组合
data class SlideFadeState(
    val offset: State<Float>,
    val alpha: State<Float>
)

@Composable
fun slideFadeIn(
    durationMillis: Int = 400,
    delayMillis: Int = 0,
    initialOffset: Float = 50f
): SlideFadeState {
    val offset = animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = EaseOutCubic
        ),
        label = "slideOffset"
    )
    val alpha = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = EaseOutCubic
        ),
        label = "slideAlpha"
    )
    return SlideFadeState(offset, alpha)
}

// 列表项依次进入动画
@Composable
fun listItemAnimateEnter(
    index: Int,
    delayPerItem: Int = 50,
    durationMillis: Int = 300
): SlideFadeState {
    return slideFadeIn(
        durationMillis = durationMillis,
        delayMillis = index * delayPerItem,
        initialOffset = 30f
    )
}

// 淡入动画
@Composable
fun fadeIn(
    durationMillis: Int = 300,
    delayMillis: Int = 0
): State<Float> {
    return animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = EaseOutCubic
        ),
        label = "fadeIn"
    )
}

// 简单的点击缩放
@Composable
fun clickScale(
    pressed: Boolean
): State<Float> {
    return animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(
            durationMillis = 150,
            easing = EaseInOutQuad
        ),
        label = "clickScale"
    )
}
