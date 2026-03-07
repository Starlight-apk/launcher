package com.pkgbash.launcher.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pkgbash.launcher.manager.GameManager
import com.pkgbash.launcher.manager.JavaManager

@Composable
fun LauncherNavHost(
    selectedTab: String,
    gameManager: GameManager,
    javaManager: JavaManager,
    modifier: Modifier = Modifier
) {
    when (selectedTab) {
        "home" -> HomeScreen(
            gameManager = gameManager,
            onStartClick = { gameManager.startGame(it) },
            onManageClick = { }
        )
        "versions" -> VersionsScreen(
            onInstallClick = { }
        )
        "mods" -> ModsScreen(
            onInstallClick = { }
        )
        "settings" -> SettingsScreen(
            javaManager = javaManager
        )
    }
}
