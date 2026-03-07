# 蔚蓝档案启动器 - PKG-BASH Launcher

> 版本：1.0.0  
> 目标平台：Android 10+ (API 29+)  
> 设计风格：Material Design 3 + 拟态设计

---

## 📁 项目结构

```
PKG-BASH-Launcher/
├── app/
│   ├── src/main/
│   │   ├── java/com/pkgbash/launcher/
│   │   │   ├── MainActivity.kt          # 主界面
│   │   │   ├── ui/
│   │   │   │   ├── theme/Theme.kt       # MD3 主题
│   │   │   │   ├── components/          # UI 组件
│   │   │   │   │   ├── NavigationRail.kt
│   │   │   │   │   ├── GameCard.kt
│   │   │   │   │   └── DownloadDialog.kt
│   │   │   │   └── animations/          # 动画
│   │   │   │       └── Animations.kt
│   │   │   ├── manager/
│   │   │   │   ├── GameManager.kt       # 游戏管理
│   │   │   │   ├── JavaInstaller.kt     # Java 安装
│   │   │   │   ├── PermissionManager.kt # 权限管理
│   │   │   │   └── DownloadManager.kt   # 下载管理
│   │   │   ├── model/
│   │   │   │   ├── Game.kt              # 游戏数据
│   │   │   │   └── User.kt              # 用户数据
│   │   │   └── util/
│   │   │       └── GitHubApi.kt         # GitHub API
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   └── themes.xml
│   │   │   ├── drawable/
│   │   │   └── mipmap/
│   │   ├── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── build.gradle.kts
├── .github/workflows/
│   └── build.yml                        # GitHub Actions
├── gradle.properties
├── settings.gradle.kts
└── README.md
```

---

## 🎨 界面设计

### 主界面布局
```
┌────────────────────────────────────────────────────┐
│  [Logo] PKG-BASH                    [👤] [⚙️] [📥] │ ← 顶部栏
├────────────┬───────────────────────────────────────┤
│            │                                       │
│  导航栏    │           内容区域                     │
│  (空白)    │                                       │
│            │  ┌─────────────────────────────┐     │
│            │  │  欢迎回来，用户              │     │
│            │  └─────────────────────────────┘     │
│            │                                       │
│            │  ┌─────────────┐ ┌─────────────┐     │
│            │  │  BA 游戏     │ │ 卡牌游戏    │     │
│            │  │  [下载]     │ │ [开发中]    │     │
│            │  └─────────────┘ └─────────────┘     │
│            │                                       │
│            │  ┌─────────────────────────────┐     │
│            │  │      [开始游戏]              │     │
│            │  └─────────────────────────────┘     │
│            │                                       │
└────────────┴───────────────────────────────────────┘
```

### 颜色方案 (MD3)
```kotlin
// 蔚蓝档案主题色
val BlueArchivePrimary = Color(0xFF1E88E5)      // 蓝色
val BlueArchiveSecondary = Color(0xFF42A5F5)    // 浅蓝
val BlueArchiveTertiary = Color(0xFF1565C0)     // 深蓝
val Background = Color(0xFF0F1419)              // 深色背景
val Surface = Color(0xFF1A1F29)                 // 卡片背景
```

---

## 🔧 核心功能

### 1. 权限请求
```kotlin
// 首次启动请求权限
- READ_EXTERNAL_STORAGE
- WRITE_EXTERNAL_STORAGE
- MANAGE_EXTERNAL_STORAGE
```

### 2. 数据目录
```
/sdcard/pkg-bash/
├── games/
│   ├── bluearchive/          # BA 游戏
│   │   ├── game.jar
│   │   ├── java/
│   │   └── config.json
│   └── cardgame/             # 卡牌游戏 (预留)
├── java/
│   └── jdk-21/               # Java 环境
└── cache/                    # 缓存
```

### 3. Java 安装流程
```
首次启动
  ↓
检查 Java 是否存在
  ↓
不存在 → 下载 Java 21 (约 50MB)
  ↓
解压到 /sdcard/pkg-bash/java/jdk-21/
  ↓
配置环境变量
  ↓
完成
```

### 4. 游戏下载
```
点击下载
  ↓
连接 GitHub 私人仓库 (pkg-bash)
  ↓
使用 SSH Key 认证
  ↓
下载游戏包 (约 900MB)
  ↓
验证 SHA256
  ↓
解压到 /sdcard/pkg-bash/games/bluearchive/
  ↓
配置依赖
  ↓
完成
```

---

## 📦 GitHub 仓库配置

### 仓库信息
- **名称**: `pkg-bash`
- **类型**: 私人仓库
- **SSH Key**: `SHA256:AwvI197h9Rw6sgEePz6rNJekawwubf214eWbg+Tu008`

### 仓库结构
```
pkg-bash/
├── games/
│   ├── bluearchive/
│   │   ├── BlueArchive-Game-FatJar-1.0.0.jar
│   │   ├── resources.zip
│   │   └── manifest.json
│   └── cardgame/
│       └── (预留)
├── java/
│   └── openjdk-21-linux-arm64.tar.gz
└── README.md
```

---

## 🎯 编译流程

### 本地编译 (需要 Android SDK)
```bash
cd PKG-BASH-Launcher
./gradlew assembleDebug
# 输出：app/build/outputs/apk/debug/app-debug.apk
```

### 云端编译 (GitHub Actions)
```bash
# 推送到 GitHub
git push origin main

# Actions 自动触发
# 5-10 分钟后在 Actions 标签页下载 APK
```

---

## 📱 安装后行为

1. **首次启动**
   - 请求权限
   - 创建 `/sdcard/pkg-bash/` 目录
   - 显示用户协议
   - 安装 Java 21
   - 进入主界面

2. **主界面**
   - 显示可下载游戏
   - BA 游戏可下载
   - 卡牌游戏显示"开发中"

3. **下载游戏**
   - 连接 GitHub
   - 下载并验证
   - 自动配置

4. **启动游戏**
   - 检查 Java 环境
   - 执行启动命令
   - 游戏运行

---

## ⚠️ 注意事项

1. **GitHub Actions 限制**
   - 免费额度：2000 分钟/月
   - 单次编译：约 5-10 分钟

2. **仓库大小**
   - GitHub 免费仓库限制：1GB
   - 建议使用 Git LFS 存储大文件

3. **权限**
   - 需要用户手动授权
   - Android 11+ 需要特殊权限

---

*项目创建时间：2026-03-06*  
*目标编译：GitHub Actions*
