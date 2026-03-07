@file:OptIn(ExperimentalMaterial3Api::class)
package com.pkgbash.launcher.ui.screens

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pkgbash.launcher.model.GameInstance
import com.pkgbash.launcher.ui.animations.*
import com.pkgbash.launcher.ui.components.GlassCard
import com.pkgbash.launcher.ui.theme.*

@Composable
fun HomeScreen(
    username: String,
    gameInstances: List<GameInstance>,
    onStartClick: (GameInstance) -> Unit,
    onManageClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 欢迎卡片
        item {
            val slideFade = slideFadeIn(durationMillis = 400, delayMillis = 100)
            WelcomeCard(
                username = username,
                modifier = Modifier
                    
            )
        }

        // 快速启动
        if (gameInstances.isNotEmpty()) {
            item {
                val slideFade = slideFadeIn(durationMillis = 400, delayMillis = 200)
                QuickStartCard(
                    instance = gameInstances.first(),
                    onStartClick = onStartClick,
                    modifier = Modifier
                        
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
                
                TextButton(onClick = onManageClick) {
                    Text("管理", color = Primary)
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // 游戏列表
        items(gameInstances, key = { it.id }) { instance ->
            val index = gameInstances.indexOf(instance)
            val slideFade = listItemAnimateEnter(index = index + 3, delayPerItem = 80)
            
            GameInstanceCard(
                instance = instance,
                onStartClick = onStartClick,
                modifier = Modifier
                    
            )
        }

        // 添加游戏按钮
        item {
            Spacer(modifier = Modifier.height(16.dp))
            
            val slideFade = slideFadeIn(durationMillis = 400, delayMillis = 500)
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    ,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "安装新游戏",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun WelcomeCard(
    username: String,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier.fillMaxWidth().height(120.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "欢迎回来",
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineMedium,
                    color = OnSurface,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatChip(label = "游戏数", value = "2")
                    StatChip(label = "已安装", value = "1")
                }
            }
        }
    }
}

@Composable
fun QuickStartCard(
    instance: GameInstance,
    onStartClick: (GameInstance) -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier.fillMaxWidth().height(80.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Success, SuccessLight)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
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
                modifier = Modifier.height(48.dp).width(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Success)
            ) {
                Text("启动", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun GameInstanceCard(
    instance: GameInstance,
    onStartClick: (GameInstance) -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier.fillMaxWidth().height(100.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(colors = PrimaryGradient)
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
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = instance.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = OnSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Badge(
                            containerColor = Primary.copy(alpha = 0.2f),
                            contentColor = Primary
                        ) {
                            Text(text = instance.version, style = MaterialTheme.typography.labelSmall)
                        }
                        Badge(
                            containerColor = SurfaceVariant,
                            contentColor = OnSurfaceVariant
                        ) {
                            Text(text = "Java ${instance.javaVersion}", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
            
            Button(
                onClick = { onStartClick(instance) },
                modifier = Modifier.height(48.dp).width(100.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
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

@Composable
fun StatChip(label: String, value: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceHighlight)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
