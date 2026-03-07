package com.pkgbash.launcher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pkgbash.launcher.ui.components.GlassCard
import com.pkgbash.launcher.ui.theme.*

@Composable
fun DownloadScreen(
    onVersionClick: () -> Unit,
    onModClick: () -> Unit,
    onShaderClick: () -> Unit,
    onResourcePackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "下载中心",
            style = MaterialTheme.typography.headlineMedium,
            color = OnSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "下载游戏版本、模组、光影和资源包",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        var selectedCategory by remember { mutableStateOf("all") }
        
        ScrollableTabRow(
            selectedTabIndex = when (selectedCategory) {
                "all" -> 0
                "versions" -> 1
                "mods" -> 2
                "shaders" -> 3
                "resourcepacks" -> 4
                else -> 0
            },
            edgePadding = 0.dp,
            containerColor = SurfaceVariant,
            contentColor = OnSurfaceVariant
        ) {
            Tab(selected = selectedCategory == "all", onClick = { selectedCategory = "all" }) {
                Text("全部", modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(selected = selectedCategory == "versions", onClick = { selectedCategory = "versions" }) {
                Text("版本", modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(selected = selectedCategory == "mods", onClick = { selectedCategory = "mods" }) {
                Text("模组", modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(selected = selectedCategory == "shaders", onClick = { selectedCategory = "shaders" }) {
                Text("光影", modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(selected = selectedCategory == "resourcepacks", onClick = { selectedCategory = "resourcepacks" }) {
                Text("资源包", modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (selectedCategory) {
            "all" -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        DownloadCategoryCard(
                            icon = Icons.Default.Computer,
                            title = "游戏版本",
                            description = "安装 Minecraft 各版本",
                            onClick = onVersionClick
                        )
                    }
                    item {
                        DownloadCategoryCard(
                            icon = Icons.Default.Extension,
                            title = "模组",
                            description = "Forge/Fabric 模组",
                            onClick = onModClick
                        )
                    }
                    item {
                        DownloadCategoryCard(
                            icon = Icons.Default.BlurOn,
                            title = "光影包",
                            description = "光影效果包",
                            onClick = onShaderClick
                        )
                    }
                    item {
                        DownloadCategoryCard(
                            icon = Icons.Default.Palette,
                            title = "资源包",
                            description = "材质/贴图包",
                            onClick = onResourcePackClick
                        )
                    }
                }
            }
            else -> {
                Text("功能开发中...", color = OnSurfaceVariant)
            }
        }
    }
}

@Composable
fun DownloadCategoryCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(colors = PrimaryGradient)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = OnSurface,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant
            )
        }
    }
}
