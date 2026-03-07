package com.pkgbash.launcher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pkgbash.launcher.model.MinecraftVersion
import com.pkgbash.launcher.model.VersionType
import com.pkgbash.launcher.ui.animations.slideFadeIn
import com.pkgbash.launcher.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersionsScreen(
    onInstallClick: (MinecraftVersion) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("all") }
    
    // 模拟版本列表
    val versions = remember {
        listOf(
            MinecraftVersion("1.20.4", VersionType.RELEASE, "", "", ""),
            MinecraftVersion("1.20.3", VersionType.RELEASE, "", "", ""),
            MinecraftVersion("1.20.2", VersionType.RELEASE, "", "", ""),
            MinecraftVersion("1.20.1", VersionType.RELEASE, "", "", ""),
            MinecraftVersion("1.20", VersionType.RELEASE, "", "", ""),
            MinecraftVersion("1.19.4", VersionType.RELEASE, "", "", ""),
            MinecraftVersion("23w51b", VersionType.SNAPSHOT, "", "", ""),
            MinecraftVersion("23w51a", VersionType.SNAPSHOT, "", "", "")
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        // 标题
        Text(
            text = "游戏版本",
            style = MaterialTheme.typography.headlineMedium,
            color = OnSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "选择并安装 Minecraft 版本",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 筛选器
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedFilter == "all",
                onClick = { selectedFilter = "all" },
                label = { Text("全部") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary
                )
            )
            
            FilterChip(
                selected = selectedFilter == "release",
                onClick = { selectedFilter = "release" },
                label = { Text("正式版") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary
                )
            )
            
            FilterChip(
                selected = selectedFilter == "snapshot",
                onClick = { selectedFilter = "snapshot" },
                label = { Text("快照版") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary
                )
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 版本列表
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(versions, key = { it.id }) { version ->
                val index = versions.indexOf(version)
                val slideFade = slideFadeIn(
                    durationMillis = 300,
                    delayMillis = index * 30
                )
                
                VersionListItem(
                    version = version,
                    onInstallClick = { onInstallClick(version) },
                    modifier = Modifier.alpha(slideFade.alpha.value)
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun VersionListItem(
    version: MinecraftVersion,
    onInstallClick: (MinecraftVersion) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 版本类型图标
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            when (version.type) {
                                VersionType.RELEASE -> Brush.horizontalGradient(
                                    colors = listOf(Primary, PrimaryContainer)
                                )
                                VersionType.SNAPSHOT -> Brush.horizontalGradient(
                                    colors = listOf(Secondary, SecondaryContainer)
                                )
                                else -> Brush.horizontalGradient(
                                    colors = listOf(Surface, SurfaceVariant)
                                )
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (version.type) {
                            VersionType.SNAPSHOT -> Icons.Default.BugReport
                            else -> Icons.Default.Computer
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Column {
                    Text(
                        text = version.id,
                        style = MaterialTheme.typography.titleMedium,
                        color = OnSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(2.dp))
                    
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = when (version.type) {
                                    VersionType.RELEASE -> "正式版"
                                    VersionType.SNAPSHOT -> "快照"
                                    else -> "其他"
                                },
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = when (version.type) {
                                VersionType.RELEASE -> Primary.copy(alpha = 0.2f)
                                VersionType.SNAPSHOT -> Secondary.copy(alpha = 0.2f)
                                else -> Surface
                            },
                            labelColor = OnSurfaceVariant
                        )
                    )
                }
            }
            
            Button(
                onClick = { onInstallClick(version) },
                modifier = Modifier
                    .height(44.dp)
                    .width(100.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("安装")
            }
        }
    }
}
