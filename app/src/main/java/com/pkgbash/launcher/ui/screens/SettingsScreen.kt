package com.pkgbash.launcher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pkgbash.launcher.ui.theme.*

@Composable
fun ModsScreen(
    onInstallClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "模组管理",
            style = MaterialTheme.typography.headlineMedium,
            color = OnSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "安装和管理 Forge/Fabric 模组",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 模组分类
        var selectedCategory by remember { mutableStateOf("all") }
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedCategory == "all",
                onClick = { selectedCategory = "all" },
                label = { Text("全部") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary
                )
            )
            FilterChip(
                selected = selectedCategory == "installed",
                onClick = { selectedCategory = "installed" },
                label = { Text("已安装") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary
                )
            )
            FilterChip(
                selected = selectedCategory == "updates",
                onClick = { selectedCategory = "updates" },
                label = { Text("更新") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary
                )
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 空状态
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Extension,
                    contentDescription = null,
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "暂无模组",
                    style = MaterialTheme.typography.titleMedium,
                    color = OnSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "点击上方按钮浏览和安装模组",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    javaManager: com.pkgbash.launcher.manager.JavaManager
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "设置",
                style = MaterialTheme.typography.headlineMedium,
                color = OnSurface,
                fontWeight = FontWeight.SemiBold
            )
        }

        // 游戏设置
        item {
            SettingsCategoryHeader("游戏")
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Games,
                title = "游戏路径",
                subtitle = "/sdcard/pkg-bash/games",
                onClick = { }
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Computer,
                title = "Java 版本",
                subtitle = "Java 21",
                onClick = { }
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Memory,
                title = "最大内存",
                subtitle = "2048 MB",
                onClick = { }
            )
        }

        // 下载设置
        item {
            SettingsCategoryHeader("下载")
        }

        item {
            SettingsItem(
                icon = Icons.Default.Folder,
                title = "下载路径",
                subtitle = "/sdcard/pkg-bash/downloads",
                onClick = { }
            )
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Speed,
                title = "下载线程数",
                subtitle = "4",
                onClick = { }
            )
        }

        // 外观设置
        item {
            SettingsCategoryHeader("外观")
        }
        
        item {
            SettingsItem(
                icon = Icons.Default.Palette,
                title = "主题",
                subtitle = "深色",
                onClick = { }
            )
        }

        // 关于
        item {
            SettingsCategoryHeader("关于")
        }

        item {
            SettingsItem(
                icon = Icons.Default.Info,
                title = "应用版本",
                subtitle = "PKG-BASH Launcher v1.0.0",
                onClick = { }
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun SettingsCategoryHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = Primary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        androidx.compose.ui.graphics.Brush.horizontalGradient(
                            colors = listOf(Primary.copy(alpha = 0.2f), Secondary.copy(alpha = 0.1f))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = OnSurface,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = OnSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
