package com.pkgbash.launcher.model

/**
 * 用户设置
 */
data class UserSettings(
    val theme: ThemeMode = ThemeMode.SYSTEM,
    val language: String = "zh-CN",
    val downloadPath: String = "/sdcard/pkg-bash/downloads",
    val gamePath: String = "/sdcard/pkg-bash/games",
    val javaPath: String = "/sdcard/pkg-bash/java",
    val maxDownloadThreads: Int = 4,
    val autoInstallModDeps: Boolean = true,
    val showSnapshots: Boolean = false,
    val enableGameLog: Boolean = true,
    val enableCrashReport: Boolean = true,
    val enableAutoSave: Boolean = true,
    val renderType: RenderType = RenderType.MG,
    val uiMode: UIMode = UIMode.ZL2,
    val enableBlur: Boolean = true,
    val blurRadius: Int = 20,
    val enableTransparency: Boolean = true,
    val animationScale: Float = 1f,
    val enableNotifications: Boolean = true,
    val downloadNotification: Boolean = true,
    val buildNotification: Boolean = true
)

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

enum class RenderType {
    MG,        // 原生渲染
    ZL2,       // ZL2 风格渲染
    CUSTOM     // 自定义渲染
}

enum class UIMode {
    ZL2,       // ZL2 风格界面
    FCL,       // FCL 风格界面
    SIMPLE     // 简洁模式
}

/**
 * 账户信息
 */
data class Account(
    val id: String = java.util.UUID.randomUUID().toString(),
    val username: String,
    val uuid: String? = null,
    val accessToken: String? = null,
    val clientToken: String? = null,
    val accountType: AccountType = AccountType.OFFLINE,
    val skinUrl: String? = null,
    val capeUrl: String? = null,
    val isSelected: Boolean = false
)

enum class AccountType {
    OFFLINE,
    MICROSOFT,
    MOJANG,
    AUTHLIB_INJECTOR
}

/**
 * 服务器信息
 */
data class ServerInfo(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val ip: String,
    val port: Int = 25565,
    val version: String? = null,
    val players: Int = 0,
    val maxPlayers: Int = 0,
    val motd: String? = null,
    val iconUrl: String? = null,
    val isFavorite: Boolean = false,
    val lastPing: Long? = null
)
