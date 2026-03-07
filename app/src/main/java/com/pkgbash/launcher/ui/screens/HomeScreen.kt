package com.pkgbash.launcher.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pkgbash.launcher.manager.GameManager
import com.pkgbash.launcher.model.GameInstance
import com.pkgbash.launcher.ui.animations.slideFadeIn
import com.pkgbash.launcher.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    gameManager: GameManager,
    onStartClick: (GameInstance) -> Unit,
    onManageClick: (GameInstance) -> Unit
) {
    val gameInstances by gameManager.gameInstances.collectAsState()
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 顶部欢迎区
        item {
            val slideFade = slideFadeIn(durationMillis = 500, delayMillis = 0)
            WelcomeHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (slideFade.alpha.value > 0f) {
                            Modifier
                                .alpha(slideFade.alpha.value)
                                .scale(0.9f + (slideFade.offset.value.coerceIn(-50f, 0f) / 50f) * 0.1f)
                        } else {
                            Modifier
                        }
                    )
            )
        }
        
        // 快速启动卡片
        if (gameInstances.isNotEmpty()) {
            item {
                val slideFade = slideFadeIn(durationMillis = 500, delayMillis = 100)
                val targetInstance = gameInstances.firstOrNull { it.lastPlayed != null } ?: gameInstances.first()
                QuickStartCard(
                    instance = targetInstance,
                    onStartClick = onStartClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (slideFade.alpha.value > 0f) {
                                Modifier
                                    .alpha(slideFade.alpha.value)
                                    .scale(0.9f + (slideFade.offset.value.coerceIn(-50f, 0f) / 50f) * 0.1f)
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }
        
        // 游戏列表标题
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "我的游戏",
                    style = MaterialTheme.typography.titleLarge,
                    color = OnSurface,
                    fontWeight = FontWeight.SemiBold
                )
                
                TextButton(onClick = { }) {
                    Text("全部", color = Primary)
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        
        // 游戏实例列表
        items(gameInstances, key = { it.id }) { instance ->
            val index = gameInstances.indexOf(instance)
            val slideFade = slideFadeIn(
                durationMillis = 400,
                delayMillis = 200 + index * 50
            )
            
            GameInstanceCard(
                instance = instance,
                onStartClick = onStartClick,
                onManageClick = { onManageClick(instance) },
                modifier = Modifier
                    .then(
                        if (slideFade.alpha.value > 0f) {
                            Modifier
                                .alpha(slideFade.alpha.value)
                                .scale(0.95f + (slideFade.offset.value.coerceIn(-30f, 0f) / 30f) * 0.05f)
                        } else {
                            Modifier
                        }
                    )
            )
        }
        
        // 添加游戏按钮
        item {
            Spacer(modifier = Modifier.height(8.dp))
            
            val slideFade = slideFadeIn(durationMillis = 500, delayMillis = 500)
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .then(
                        if (slideFade.alpha.value > 0f) {
                            Modifier.alpha(slideFade.alpha.value)
                        } else {
                            Modifier
                        }
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "安装新游戏",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun WelcomeHeader(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceVariant
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "欢迎回来",
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "准备好开始冒险了吗？",
                    style = MaterialTheme.typography.headlineMedium,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatChip(
                        icon = Icons.Default.Games,
                        label = "游戏",
                        value = "2"
                    )
                    StatChip(
                        icon = Icons.Default.CheckCircle,
                        label = "已安装",
                        value = "1"
                    )
                }
            }
            
            // 装饰图标
            Icon(
                imageVector = Icons.Default.Games,
                contentDescription = null,
                tint = Primary.copy(alpha = 0.1f),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
fun QuickStartCard(
    instance: GameInstance,
    onStartClick: (GameInstance) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceVariant
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.15f),
                            Secondary.copy(alpha = 0.1f)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 启动图标
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Success, Success.copy(alpha = 0.7f))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    
                    Column {
                        Text(
                            text = "快速启动",
                            style = MaterialTheme.typography.labelLarge,
                            color = OnSurfaceVariant
                        )
                        Text(
                            text = instance.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = OnSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                
                Button(
                    onClick = { onStartClick(instance) },
                    modifier = Modifier
                        .height(52.dp)
                        .width(140.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Success
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "启动",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameInstanceCard(
    instance: GameInstance,
    onStartClick: (GameInstance) -> Unit,
    onManageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
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
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 游戏图标
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = PrimaryGradient
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = instance.name.firstOrNull()?.toString() ?: "M",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column {
                    Text(
                        text = instance.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = OnSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AssistChip(
                            onClick = { },
                            label = {
                                Text(
                                    text = instance.version,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Tag,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Primary.copy(alpha = 0.2f),
                                labelColor = Primary
                            )
                        )
                        
                        AssistChip(
                            onClick = { },
                            label = {
                                Text(
                                    text = "Java ${instance.javaVersion}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Surface,
                                labelColor = OnSurfaceVariant
                            )
                        )
                    }
                }
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onManageClick,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Surface),
                    content = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "管理",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )
                
                Button(
                    onClick = { onStartClick(instance) },
                    modifier = Modifier
                        .height(48.dp)
                        .width(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("启动")
                }
            }
        }
    }
}

@Composable
fun StatChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Surface)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = Primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant
        )
    }
}
