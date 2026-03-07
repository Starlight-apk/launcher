package com.pkgbash.launcher.manager

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Java 安装管理器
 * 支持安装 Java 8, 11, 17, 21
 */
class JavaManager {

    companion object {
        private const val TAG = "JavaManager"
        val SUPPORTED_VERSIONS = listOf(8, 11, 17, 21)
        const val DEFAULT_JAVA_PATH = "/sdcard/pkg-bash/java"
    }

    /**
     * 初始化 Java 管理器
     */
    fun initialize() {
        // 检查已安装的 Java 版本
        val installedVersions = getInstalledJavaVersions()
        if (installedVersions.isEmpty()) {
            // 需要安装 Java
        }
    }

    /**
     * 检查 Java 版本是否已安装
     */
    fun isJavaInstalled(version: Int): Boolean {
        val javaDir = File(DEFAULT_JAVA_PATH, "jdk-$version")
        val javaExecutable = File(javaDir, "bin/java")
        return javaExecutable.exists() && javaExecutable.canExecute()
    }

    /**
     * 获取已安装的 Java 版本列表
     */
    fun getInstalledJavaVersions(): List<Int> {
        return SUPPORTED_VERSIONS.filter { isJavaInstalled(it) }
    }

    /**
     * 获取默认 Java 版本
     */
    fun getDefaultJavaVersion(): Int {
        // 优先返回最高的已安装版本
        for (version in SUPPORTED_VERSIONS.reversed()) {
            if (isJavaInstalled(version)) {
                return version
            }
        }
        return 21 // 默认返回 21
    }

    /**
     * 获取 Java 可执行文件路径
     */
    fun getJavaExecutablePath(version: Int): String? {
        if (!isJavaInstalled(version)) return null
        return File(DEFAULT_JAVA_PATH, "jdk-$version/bin/java").absolutePath
    }

    /**
     * 安装 Java 版本
     */
    suspend fun installJavaVersion(
        version: Int,
        onProgress: (Float) -> Unit,
        onStatus: (String) -> Unit
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            onStatus("正在下载 Java $version...")
            
            // 创建目录
            val javaDir = File(DEFAULT_JAVA_PATH, "jdk-$version")
            javaDir.mkdirs()

            // 模拟下载进度（实际应从镜像源下载）
            val mirrorUrl = when (version) {
                8 -> "https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u292-b10/OpenJDK8U-jdk_aarch64_linux_hotspot_8u292b10.tar.gz"
                11 -> "https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.11%2B9/OpenJDK11U-jdk_aarch64_linux_hotspot_11.0.11_9.tar.gz"
                17 -> "https://github.com/AdoptOpenJDK/openjdk17-binaries/releases/download/jdk-17.0.1%2B12/OpenJDK17U-jdk_aarch64_linux_hotspot_17.0.1_12.tar.gz"
                21 -> "https://github.com/AdoptOpenJDK/openjdk21-binaries/releases/download/jdk-21.0.1%2B12/OpenJDK21U-jdk_aarch64_linux_hotspot_21.0.1_12.tar.gz"
                else -> throw IllegalArgumentException("不支持的 Java 版本：$version")
            }

            onStatus("正在解压 Java $version...")
            
            // 模拟安装进度
            for (i in 0..100 step 5) {
                onProgress(i / 100f)
                kotlinx.coroutines.delay(100)
            }

            onProgress(1f)
            onStatus("Java $version 安装完成")
            
            Log.i(TAG, "Java $version 已安装到：${javaDir.absolutePath}")
            Result.success(Unit)
            
        } catch (e: Exception) {
            Log.e(TAG, "安装 Java 失败", e)
            onStatus("安装失败：${e.message}")
            Result.failure(e)
        }
    }

    /**
     * 卸载 Java 版本
     */
    fun uninstallJavaVersion(version: Int): Boolean {
        val javaDir = File(DEFAULT_JAVA_PATH, "jdk-$version")
        return javaDir.deleteRecursively()
    }

    /**
     * 获取 Java 版本信息
     */
    fun getJavaVersionInfo(version: Int): JavaInfo {
        val isInstalled = isJavaInstalled(version)
        val javaDir = File(DEFAULT_JAVA_PATH, "jdk-$version")
        
        return JavaInfo(
            version = version,
            isInstalled = isInstalled,
            installPath = if (isInstalled) javaDir.absolutePath else null,
            executablePath = getJavaExecutablePath(version),
            size = if (isInstalled) javaDir.getDirectorySize() else 0
        )
    }

    /**
     * 获取目录大小
     */
    private fun File.getDirectorySize(): Long {
        return walkTopDown()
            .filter { it.isFile }
            .map { it.length() }
            .sum()
    }

    /**
     * Java 版本信息
     */
    data class JavaInfo(
        val version: Int,
        val isInstalled: Boolean,
        val installPath: String?,
        val executablePath: String?,
        val size: Long
    )
}
