package com.pkgbash.launcher.ui.components

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
import com.pkgbash.launcher.ui.animations.slideFadeIn
import com.pkgbash.launcher.ui.theme.*

data class NavItem(
    val id: String,
    val icon: ImageVector,
    val iconOutlined: ImageVector,
    val label: String
)

val defaultNavItems = listOf(
    NavItem("home", Icons.Filled.Home, Icons.Outlined.Home, "主页"),
    NavItem("download", Icons.Filled.Download, Icons.Outlined.Download, "下载"),
    NavItem("mods", Icons.Filled.Extension, Icons.Outlined.Extension, "模组"),
    NavItem("settings", Icons.Filled.Settings, Icons.Outlined.Settings, "设置")
)

@Composable
fun NavigationRail(
    selectedItem: String = "home",
    onItemSelected: (String) -> Unit = {},
    navItems: List<NavItem> = defaultNavItems
) {
    Column(
        modifier = Modifier
            .width(88.dp)
            .fillMaxHeight()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Surface, SurfaceVariant)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Spacer(modifier = Modifier.height(20.dp))
        
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = PrimaryGradient
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PK",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "BASH",
            style = MaterialTheme.typography.labelMedium,
            color = Primary
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 导航项
        navItems.forEachIndexed { index, item ->
            val isSelected = selectedItem == item.id
            val slideFade = slideFadeIn(
                durationMillis = 300,
                delayMillis = index * 80,
                initialOffset = 20f
            )

            NavigationRailItem(
                item = item,
                selected = isSelected,
                onClick = { onItemSelected(item.id) },
                modifier = Modifier
                    .run {
                        var mod = this
                        if (slideFade.alpha.value > 0f) {
                            mod = mod
                        }
                        mod
                    }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        
        // 版本信息
        Text(
            text = "v1.0.0",
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
fun NavigationRailItem(
    item: NavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Primary.copy(alpha = 0.3f),
                                Secondary.copy(alpha = 0.2f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = Primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        } else {
            Icon(
                imageVector = item.iconOutlined,
                contentDescription = item.label,
                tint = OnSurfaceVariant,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = item.label,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) Primary else OnSurfaceVariant
        )

        if (selected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Primary, Secondary)
                        )
                    )
            )
        }
    }
}
