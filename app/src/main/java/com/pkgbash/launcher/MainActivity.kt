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
import com.pkgbash.launcher.manager.GameManager
import com.pkgbash.launcher.manager.JavaManager
import com.pkgbash.launcher.ui.screens.LauncherNavHost
import com.pkgbash.launcher.ui.screens.BottomNavBar
import com.pkgbash.launcher.ui.theme.*

/**
 * PKG-BASH Launcher - 主活动
 * 完整的 Minecraft 启动器
 */
class MainActivity : ComponentActivity() {

    private lateinit var gameManager: GameManager
    private lateinit var javaManager: JavaManager

    private var selectedTab by mutableStateOf("home")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化管理器
        gameManager = GameManager(this)
        javaManager = JavaManager()

        // 初始化
        gameManager.initialize()
        javaManager.initialize()

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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 主内容区
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LauncherNavHost(
                    selectedTab = selectedTab,
                    gameManager = gameManager,
                    javaManager = javaManager
                )
            }

            // 底部导航栏
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    }
}
