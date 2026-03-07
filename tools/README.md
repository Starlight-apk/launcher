# PKG-BASH Launcher 构建监控工具

## 📁 工具列表

### 1. `monitor-build.sh` - 构建监控脚本
实时监控 GitHub Actions 构建状态，失败时输出详细错误。

### 2. `git-push-and-monitor.sh` - 推送 + 监控一体化
自动提交、推送并启动监控。

---

## 🚀 快速开始

### 方法 1: 推送 + 监控（推荐）

```bash
cd /media/sd/Kaifa/apk

# 自动提交并推送，然后监控构建
./tools/git-push-and-monitor.sh "修复 UI 问题"
```

### 方法 2: 仅监控

```bash
# 如果已经推送，直接监控
./tools/monitor-build.sh
```

### 方法 3: 仅推送（不监控）

```bash
# 只推送，不占用前端
./tools/git-push-and-monitor.sh -n "更新代码"

# 然后可以手动运行监控
./tools/monitor-build.sh
```

---

## 📋 详细用法

### monitor-build.sh

```bash
# 基本用法
./tools/monitor-build.sh

# 使用 GitHub Token（更高 API 限流）
GITHUB_TOKEN=your_token_here ./tools/monitor-build.sh

# 自定义轮询间隔（秒）
POLL_INTERVAL=5 ./tools/monitor-build.sh

# 自定义最大等待时间（秒）
MAX_WAIT_TIME=3600 ./tools/monitor-build.sh

# 调试模式
DEBUG=1 ./tools/monitor-build.sh
```

**功能：**
- ⏳ 自动检测最新构建
- 🔄 实时轮询状态（默认 8 秒）
- ✅ 成功时显示下载链接
- ❌ 失败时输出错误详情
- 📊 显示所有 Job 状态

---

### git-push-and-monitor.sh

```bash
# 自动提交信息
./tools/git-push-and-monitor.sh

# 自定义提交信息
./tools/git-push-and-monitor.sh "feat: 添加新功能"

# 只推送不监控
./tools/git-push-and-monitor.sh -n "更新代码"

# 使用自定义远程和分支
GITHUB_REMOTE=upstream GITHUB_BRANCH=dev ./tools/git-push-and-monitor.sh
```

**流程：**
1. 检查 Git 状态
2. 自动添加所有更改
3. 提交（自动或自定义信息）
4. 推送到 GitHub
5. 等待 GitHub 接收
6. 启动构建监控

---

## 🔧 环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| `GITHUB_TOKEN` | GitHub API Token | 无 |
| `GITHUB_REPO` | 仓库名（格式：user/repo） | Starlight-apk/launcher |
| `GITHUB_REMOTE` | Git 远程仓库名 | origin |
| `GITHUB_BRANCH` | 分支名 | main |
| `POLL_INTERVAL` | 轮询间隔（秒） | 8 |
| `MAX_WAIT_TIME` | 最大等待时间（秒） | 1800 |
| `DEBUG` | 调试模式 | 0 |

---

## 📦 依赖

- `bash` - Bash shell
- `curl` - HTTP 客户端
- `jq` - JSON 处理器

### 安装依赖

**Ubuntu/Debian:**
```bash
sudo apt install -y curl jq
```

**macOS:**
```bash
brew install curl jq
```

**CentOS/RHEL:**
```bash
sudo yum install -y curl jq
```

---

## 🎯 使用场景

### 场景 1: 日常开发推送

```bash
# 修改代码后
./tools/git-push-and-monitor.sh "fix: 修复启动器崩溃"
```

**输出示例：**
```
╔═══════════════════════════════════════════════════╗
║     PKG-BASH Launcher 推送 + 监控一体化脚本       ║
╚═══════════════════════════════════════════════════╝

[INFO] Git 检查通过
[INFO] 监控脚本已准备

════════════════════════════════════════════════════
  Git 状态
════════════════════════════════════════════════════

 M app/src/main/java/com/pkgbash/launcher/MainActivity.kt

[INFO] 检测到 1 个更改

════════════════════════════════════════════════════
  提交更改
════════════════════════════════════════════════════

[INFO] 提交信息：fix: 修复启动器崩溃
[SUCCESS] 提交完成

════════════════════════════════════════════════════
  推送到 GitHub
════════════════════════════════════════════════════

[INFO] 远程：origin
[INFO] 分支：main
[SUCCESS] 推送成功

[INFO] 等待 GitHub 接收推送...

════════════════════════════════════════════════════
  启动构建监控
════════════════════════════════════════════════════

╔═══════════════════════════════════════════════════════╗
║     PKG-BASH Launcher 构建监控系统                    ║
║     GitHub Actions Real-time Monitor                  ║
╚═══════════════════════════════════════════════════════╝

[INFO] 正在获取最新构建...
[SUCCESS] 找到构建任务

构建信息:
  ID:          12345678
  名称：       Build APK
  分支：       main (abc1234)
  状态：       🔄 in_progress
  创建时间：   2024-01-01T12:00:00Z
  查看链接：   https://github.com/Starlight-apk/launcher/actions/runs/12345678

╔════════════════════════════════════════════════════╗
  开始监控
╚════════════════════════════════════════════════════╝

[INFO] 轮询间隔：8 秒
[INFO] 最大等待：1800 秒
[INFO] 按 Ctrl+C 可取消监控

[12:00:15] 状态更新：🔄 in_progress

Job 状态:
  🔄 build - in_progress

  已等待：15 秒 / 1800 秒

[12:05:30] 状态更新：✅ completed / success

────────────────────────────────────────────────────
  构建成功
────────────────────────────────────────────────────

🎉 APK 构建完成!

📦 版本：Build APK

下载方式:

  方式 1: GitHub Actions
    1. 访问 Actions 页面
    2. 点击最新构建
    3. 滚动到 'Artifacts' 区域
    4. 下载 APK 文件

  方式 2: GitHub Releases
    https://github.com/Starlight-apk/launcher/releases

  方式 3: 直接链接
    https://github.com/Starlight-apk/launcher/actions/runs/12345678

[SUCCESS] 任务完成!
```

---

### 场景 2: 查看已有构建

```bash
# 如果构建已经在运行
./tools/monitor-build.sh
```

---

### 场景 3: 后台推送 + 手动监控

```bash
# 推送（不监控）
./tools/git-push-and-monitor.sh -n "更新代码"

# 然后可以做其他事...

# 稍后手动监控
./tools/monitor-build.sh
```

---

## ⚠️ 故障排除

### 问题 1: 未找到构建任务

**原因：**
- GitHub Actions 未启用
- 推送的分支不对
- GitHub 延迟

**解决：**
```bash
# 检查 Actions 是否启用
# 访问：https://github.com/Starlight-apk/launcher/actions

# 手动启用 Actions
# 点击 "I understand my workflow, go ahead and enable it"
```

---

### 问题 2: API 限流

**错误：** `API rate limit exceeded`

**解决：**
```bash
# 使用 GitHub Token
export GITHUB_TOKEN=your_token_here

# 获取 Token:
# https://github.com/settings/tokens
# 创建 Token (无需权限，仅用于提高限流)
```

---

### 问题 3: 推送失败

**错误：** `Permission denied (publickey)`

**解决：**
```bash
# 检查 SSH 密钥
ssh -T git@github.com

# 如果未配置，添加 SSH 密钥到 GitHub
# https://github.com/settings/keys
```

---

### 问题 4: jq 未安装

**错误：** `command not found: jq`

**解决：**
```bash
# Ubuntu/Debian
sudo apt install jq

# macOS
brew install jq

# CentOS
sudo yum install jq
```

---

## 📊 监控脚本工作原理

```
┌─────────────────────────────────────────────────┐
│  1. 获取最新构建                                 │
│     GET /repos/{repo}/actions/runs              │
└─────────────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────────────┐
│  2. 检查状态                                     │
│     - completed: 显示结果                        │
│     - in_progress: 继续监控                      │
│     - queued: 等待                               │
└─────────────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────────────┐
│  3. 获取 Job 状态                                 │
│     GET /repos/{repo}/actions/runs/{id}/jobs    │
└─────────────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────────────┐
│  4. 显示进度                                     │
│     - Job 名称和状态                              │
│     - 已等待时间                                 │
└─────────────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────────────┐
│  5. 构建完成                                     │
│     - 成功：显示下载链接                         │
│     - 失败：显示错误详情                         │
└─────────────────────────────────────────────────┘
```

---

## 🎨 输出颜色说明

| 颜色 | 含义 |
|------|------|
| 🔵 蓝色 | 信息 |
| 🟢 绿色 | 成功 |
| 🔴 红色 | 错误 |
| 🟡 黄色 | 警告 |
| 🟣 青色 | 标题/强调 |

---

## 📝 脚本退出码

| 退出码 | 含义 |
|--------|------|
| 0 | 构建成功 |
| 1 | 构建失败 |
| 130 | 用户取消 (Ctrl+C) |

---

## 🔗 相关链接

- 仓库：https://github.com/Starlight-apk/launcher
- Actions: https://github.com/Starlight-apk/launcher/actions
- Releases: https://github.com/Starlight-apk/launcher/releases

---

## 💡 提示

1. **首次使用** 建议先用 `-n` 选项测试推送
2. **长时间构建** 可以调大 `MAX_WAIT_TIME`
3. **频繁推送** 建议配置 `GITHUB_TOKEN` 避免限流
4. **失败时** 脚本会显示详细错误，帮助快速定位问题

---

**最后更新:** 2024-01-01
**版本:** 1.0.0
