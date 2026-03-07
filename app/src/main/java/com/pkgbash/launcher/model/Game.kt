package com.pkgbash.launcher.model

/**
 * Minecraft 游戏版本
 */
data class MinecraftVersion(
    val id: String,
    val type: VersionType = VersionType.RELEASE,
    val url: String,
    val time: String,
    val releaseTime: String
)

enum class VersionType {
    RELEASE,
    SNAPSHOT,
    OLD_BETA,
    OLD_ALPHA
}

/**
 * 游戏实例
 */
data class GameInstance(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val version: String,
    val javaVersion: Int = 21,
    val modLoader: ModLoader = ModLoader.VANILLA,
    val modLoaderVersion: String? = null,
    val iconPath: String? = null,
    val lastPlayed: Long? = null,
    val playTime: Long = 0,
    val isFavorite: Boolean = false,
    val maxMemory: Int = 2048,
    val jvmArgs: String = ""
)

enum class ModLoader {
    VANILLA,
    FORGE,
    FABRIC,
    QUILT,
    OPTIFINE,
    SPIGOT,
    PAPER
}

/**
 * 模组
 */
data class Mod(
    val id: String,
    val name: String,
    val description: String,
    val version: String,
    val author: String,
    val iconUrl: String? = null,
    val downloadUrl: String? = null,
    val isInstalled: Boolean = false,
    val isEnabled: Boolean = true
)

/**
 * 光影包
 */
data class ShaderPack(
    val id: String,
    val name: String,
    val description: String,
    val version: String,
    val author: String,
    val iconUrl: String? = null,
    val downloadUrl: String? = null,
    val isInstalled: Boolean = false,
    val isEnabled: Boolean = false
)

/**
 * 资源包
 */
data class ResourcePack(
    val id: String,
    val name: String,
    val description: String,
    val version: String,
    val author: String,
    val iconUrl: String? = null,
    val downloadUrl: String? = null,
    val isInstalled: Boolean = false,
    val isEnabled: Boolean = false
)

/**
 * 下载任务
 */
data class DownloadTask(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val type: DownloadType,
    val url: String,
    val size: Long,
    val progress: Float = 0f,
    val status: DownloadStatus = DownloadStatus.PENDING,
    val errorMessage: String? = null
)

enum class DownloadType {
    GAME_VERSION,
    MOD,
    SHADER,
    RESOURCE_PACK,
    JAVA
}

enum class DownloadStatus {
    PENDING,
    DOWNLOADING,
    PAUSED,
    COMPLETED,
    FAILED,
    CANCELLED
}

/**
 * Java 版本信息
 */
data class JavaVersion(
    val version: Int,
    val path: String? = null,
    val isInstalled: Boolean = false,
    val isDefault: Boolean = false
)
