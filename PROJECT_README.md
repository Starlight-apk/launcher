# PKG-BASH Launcher - 蔚蓝档案启动器

> 一个类似 ZL2 的游戏启动器，专为蔚蓝档案同人游戏设计  
> 版本：1.0.0  
> 目标平台：Android 10+ (API 29+)

---

## 📁 项目结构

```
apk/
├── app/
│   ├── src/main/
│   │   ├── java/com/pkgbash/launcher/
│   │   │   ├── MainActivity.kt           # 主界面和活动
│   │   │   ├── ui/
│   │   │   │   ├── theme/                # MD3 主题
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Type.kt
│   │   │   │   │   └── Theme.kt
│   │   │   │   ├── components/           # UI 组件
│   │   │   │   │   ├── NavigationRail.kt # 导航栏
│   │   │   │   │   ├── GameCard.kt       # 游戏卡片
│   │   │   │   │   └── Dialogs.kt        # 对话框
│   │   │   │   └── animations/           # 动画
│   │   │   │       └── Animations.kt
│   │   │   ├── manager/                  # 管理器
│   │   │   │   ├── GameManager.kt        # 游戏管理
│   │   │   │   ├── PermissionManager.kt  # 权限管理
│   │   │   │   └── JavaInstaller.kt      # Java 安装
│   │   │   ├── model/                    # 数据模型
│   │   │   │   ├── Game.kt               # 游戏数据
│   │   │   │   └── User.kt               # 用户数据
│   │   │   └── util/                     # 工具类
│   │   │       └── GitHubApi.kt          # GitHub API
│   │   ├── res/                          # 资源文件
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── .github/workflows/
│   └── build.yml                         # GitHub Actions 配置
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── gradlew                               # Gradle 启动脚本
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🎨 界面设计

### 布局 (类似 ZL2)
```
┌────────────────────────────────────────────────────┐
│  [Logo] PKG-BASH              [👤] [⚙️] [📥]       │ ← 顶部栏
├────────────┬───────────────────────────────────────┤
│            │                                       │
│  导航栏    │           内容区域                     │
│  (左侧)    │                                       │
│            │  欢迎回来，用户                       │
│  [主页]    │  ┌─────────────┐ ┌─────────────┐     │
│  [下载]    │  │  BA 游戏     │ │ 卡牌游戏    │     │
│  [设置]    │  │  [下载]     │ │ [开发中]    │     │
│            │  └─────────────┘ └─────────────┘     │
│            │                                       │
│            │  ┌─────────────────────────────┐     │
│            │  │      [开始游戏]              │     │
│            │  └─────────────────────────────┘     │
└────────────┴───────────────────────────────────────┘
```

### 设计风格
- **Material Design 3** - 现代 Material 设计
- **拟态风格** - 深色背景，蓝色主色调
- **流畅动画** - 所有交互都有过渡动画

---

## 🚀 编译方法

### 方法 1：云端编译 (推荐 - GitHub Actions)

1. **推送到 GitHub**
   ```bash
   cd /media/sd/Kaifa/apk
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin https://github.com/你的用户名/PKG-BASH-Launcher.git
   git push -u origin main
   ```

2. **等待自动编译**
   - GitHub Actions 会自动触发
   - 约 5-10 分钟完成
   - 在 Actions 标签页下载 APK

3. **下载 APK**
   - 进入仓库 → Actions → 最新构建
   - 下载 `PKG-BASH-Debug` 或 `PKG-BASH-Release`

### 方法 2：本地编译 (需要 Android SDK)

```bash
cd /media/sd/Kaifa/apk
./gradlew assembleDebug
```

输出位置：
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## 📱 安装和使用

### 首次启动流程

1. **安装 APK**
   - 下载 APK 文件
   - 在安卓设备上安装

2. **授予权限**
   - 首次启动会请求权限
   - 点击"授权"
   - 在系统设置中开启"所有文件访问"

3. **安装 Java 环境**
   - 自动弹出 Java 安装对话框
   - 点击"同意并安装"
   - 等待下载和安装 (约 50MB)

4. **创建数据目录**
   ```
   /sdcard/pkg-bash/
   ├── games/          # 游戏文件
   ├── java/           # Java 环境
   └── cache/          # 缓存
   ```

5. **下载游戏**
   - 在主界面点击 BA 游戏的"下载"按钮
   - 等待下载完成 (约 950MB)
   - 游戏状态变为"已安装"

6. **开始游戏**
   - 点击"开始游戏"按钮
   - 游戏启动！

---

## 🔧 配置说明

### GitHub 私人仓库配置

在 `GitHubApi.kt` 中配置：

```kotlin
const val REPO_OWNER = "pkg-bash"
const val REPO_NAME = "pkg-bash"
const val SSH_KEY_FINGERPRINT = "SHA256:AwvI197h9Rw6sgEePz6rNJekawwubf214eWbg+Tu008"
```

### 游戏列表配置

在 `GameList.kt` 中添加游戏：

```kotlin
Game(
    id = "cardgame",
    name = "卡牌游戏",
    description = "策略卡牌游戏",
    version = "0.0.1",
    jarPath = "/sdcard/pkg-bash/games/cardgame/Game.jar",
    mainClass = "com.cardgame.Main",
    javaFxRequired = false,
    status = GameStatus.NOT_DOWNLOADED,
    isDeveloping = true  // 开发中标记
)
```

---

## ⚙️ GitHub Actions 配置

### 自动编译
- 推送代码时自动触发
- 生成 Debug 和 Release APK
- 上传为 Artifact

### 手动触发
- 进入 Actions 标签页
- 选择 "Build APK"
- 点击 "Run workflow"

### 自动发布 Release
- 推送标签时自动创建 Release
- 格式：`v1.0.0`
- 自动上传已签名的 APK

---

## 📋 权限说明

| 权限 | 用途 |
|------|------|
| `INTERNET` | 下载游戏和 Java |
| `READ_EXTERNAL_STORAGE` | 读取游戏文件 |
| `WRITE_EXTERNAL_STORAGE` | 保存游戏文件 |
| `MANAGE_EXTERNAL_STORAGE` | 所有文件访问 (Android 11+) |
| `REQUEST_INSTALL_PACKAGES` | 安装 Java 环境 |
| `FOREGROUND_SERVICE` | 后台下载 |

---

## 🎨 主题颜色

```kotlin
// 蔚蓝档案主题
Primary: #1E88E5      // 蓝色
Secondary: #42A5F5    // 浅蓝
Tertiary: #7E57C2     // 紫色
Background: #0F1419   // 深色背景
Surface: #1A1F29      // 卡片背景
```

---

## 🔗 依赖库

| 库 | 用途 |
|------|------|
| Jetpack Compose | UI 框架 |
| Material 3 | 设计系统 |
| OkHttp | 网络请求 |
| Gson | JSON 解析 |
| Kotlin Coroutines | 异步处理 |
| JGit | Git 操作 |
| JSch | SSH 连接 |

---

## ⚠️ 注意事项

1. **GitHub 仓库大小**
   - 免费仓库限制 1GB
   - 建议使用 Git LFS 存储大文件

2. **编译时间**
   - 首次编译约 10 分钟
   - 后续编译约 5 分钟

3. **权限要求**
   - Android 11+ 需要特殊权限
   - 用户需手动在设置中开启

4. **Java 环境**
   - 首次启动自动下载
   - 约 50MB 大小

---

## 📝 待办事项

- [ ] 完善账号登录系统
- [ ] 添加游戏下载进度显示
- [ ] 实现游戏更新检测
- [ ] 添加设置界面功能
- [ ] 优化动画效果
- [ ] 添加多语言支持

---

## 📄 许可证

本项目仅供学习和研究使用

---

*创建时间：2026-03-06*  
*最后更新：2026-03-06*
