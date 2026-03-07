package com.pkgbash.launcher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pkgbash.launcher.ui.theme.*

data class NavTab(
    val id: String,
    val icon: ImageVector,
    val iconOutlined: ImageVector,
    val label: String
)

val navTabs = listOf(
    NavTab("home", Icons.Filled.Home, Icons.Outlined.Home, "主页"),
    NavTab("versions", Icons.Filled.Computer, Icons.Outlined.Computer, "版本"),
    NavTab("mods", Icons.Filled.Extension, Icons.Outlined.Extension, "模组"),
    NavTab("settings", Icons.Filled.Settings, Icons.Outlined.Settings, "设置")
)

@Composable
fun BottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        containerColor = SurfaceVariant,
        contentColor = OnSurfaceVariant,
        tonalElevation = 8.dp
    ) {
        navTabs.forEach { tab ->
            val selected = selectedTab == tab.id
            
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab.id) },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .then(
                                if (selected) {
                                    Modifier.background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Primary.copy(alpha = 0.2f),
                                                Secondary.copy(alpha = 0.1f)
                                            )
                                        )
                                    )
                                } else {
                                    Modifier
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (selected) tab.icon else tab.iconOutlined,
                            contentDescription = tab.label,
                            tint = if (selected) Primary else OnSurfaceVariant,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (selected) Primary else OnSurfaceVariant,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Primary.copy(alpha = 0.1f),
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    unselectedIconColor = OnSurfaceVariant,
                    unselectedTextColor = OnSurfaceVariant
                )
            )
        }
    }
}
