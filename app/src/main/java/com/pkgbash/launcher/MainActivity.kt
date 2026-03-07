package com.pkgbash.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.pkgbash.launcher.manager.GameManager
import com.pkgbash.launcher.manager.JavaManager
import com.pkgbash.launcher.ui.components.NavigationRail
import com.pkgbash.launcher.ui.components.TopBar
import com.pkgbash.launcher.ui.screens.HomeScreen
import com.pkgbash.launcher.ui.screens.DownloadScreen
import com.pkgbash.launcher.ui.screens.SettingsScreen
import com.pkgbash.launcher.ui.theme.*

/**
 * PKG-BASH Launcher - 主活动
 * 现代化 Minecraft 游戏启动器
 */
class MainActivity : ComponentActivity() {

    private lateinit var gameManager: GameManager
    private lateinit var javaManager: JavaManager

    private var selectedNav by mutableStateOf("home")
    private var username by mutableStateOf("旅行者")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化管理器
        gameManager = GameManager(this)
        javaManager = JavaManager()

        // 初始化游戏管理器
        gameManager.initialize()

        setContent {
            PKGBASHLauncherTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = BackgroundGradient
                            )
                        ),
                    color = Background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // 左侧导航栏
                NavigationRail(
                    selectedItem = selectedNav,
                    onItemSelected = { selectedNav = it }
                )

                // 右侧内容区
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    // 顶部栏
                    TopBar(
                        username = username,
                        onNotificationClick = { },
                        onProfileClick = { }
                    )

                    // 内容区域
                    when (selectedNav) {
                        "home" -> HomeScreen(
                            username = username,
                            gameInstances = gameManager.gameInstances.collectAsState().value,
                            onStartClick = { gameManager.startGame(it) },
                            onManageClick = { }
                        )
                        "download" -> DownloadScreen(
                            onVersionClick = { },
                            onModClick = { },
                            onShaderClick = { },
                            onResourcePackClick = { }
                        )
                        "settings" -> SettingsScreen()
                    }
                }
            }
        }
    }
}
