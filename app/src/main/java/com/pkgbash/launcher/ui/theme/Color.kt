package com.pkgbash.launcher.ui.theme

import androidx.compose.ui.graphics.Color

// ===== 主色调 - 动态渐变 =====
val PrimaryStart = Color(0xFF6366F1)
val PrimaryMiddle = Color(0xFF8B5CF6)
val PrimaryEnd = Color(0xFFEC4899)

val Primary = Color(0xFF6366F1)
val PrimaryLight = Color(0xFFA5B4FC)
val PrimaryDark = Color(0xFF4338CA)
val OnPrimary = Color(0xFFFFFFFF)

// ===== 辅助色 =====
val Secondary = Color(0xFF0EA5E9)
val SecondaryLight = Color(0xFF7DD3FC)
val SecondaryDark = Color(0xFF0284C7)
val OnSecondary = Color(0xFFFFFFFF)

val Tertiary = Color(0xFF10B981)
val TertiaryLight = Color(0xFF6EE7B7)
val TertiaryDark = Color(0xFF059669)
val OnTertiary = Color(0xFFFFFFFF)

// ===== 背景色 - 毛玻璃拟态 =====
val Background = Color(0xFF0F172A)
val BackgroundSecondary = Color(0xFF1E293B)
val Surface = Color(0x801E293B)  // 半透明用于毛玻璃
val SurfaceVariant = Color(0x60334155)
val SurfaceHighlight = Color(0x403E4C61)

val OnSurface = Color(0xFFF1F5F9)
val OnSurfaceVariant = Color(0xFF94A3B8)
val OnSurfaceDisabled = Color(0xFF475569)

// ===== 毛玻璃效果色 =====
val GlassBackground = Color(0x401E293B)
val GlassBorder = Color(0x30FFFFFF)
val GlassHighlight = Color(0x20FFFFFF)

// ===== 状态色 =====
val Success = Color(0xFF22C55E)
val SuccessLight = Color(0xFF86EFAC)
val Warning = Color(0xFFF59E0B)
val WarningLight = Color(0xFFFDE68A)
val Error = Color(0xFFEF4444)
val ErrorLight = Color(0xFFFCA5A5)
val Info = Color(0xFF3B82F6)

// ===== 特殊效果色 =====
val GlowPrimary = Color(0x406366F1)
val GlowSecondary = Color(0x400EA5E9)
val GlowTertiary = Color(0x4010B981)

// ===== 渐变配置 =====
val PrimaryGradient = listOf(PrimaryStart, PrimaryMiddle, PrimaryEnd)
val BackgroundGradient = listOf(Background, BackgroundSecondary)
val CardGradient = listOf(Surface, SurfaceVariant)

// ===== Minecraft 主题色 =====
val MinecraftGreen = Color(0xFF568203)
val MinecraftDirt = Color(0xFF5D4037)
val MinecraftStone = Color(0xFF757575)
val MinecraftDiamond = Color(0xFF00CED1)
