#!/bin/bash

###############################################################################
# PKG-BASH Launcher GitHub Actions 监控脚本 (SSH 版本)
# 通过 git 和 SSH 方式获取构建状态
###############################################################################

REPO="Starlight-apk/launcher"
REPO_URL="git@github.com:${REPO}.git"
TEMP_DIR="/tmp/pkg-bash-monitor-$$"

# 颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

cleanup() {
    rm -rf "$TEMP_DIR" 2>/dev/null
}

trap cleanup EXIT

echo -e "${CYAN}"
echo "╔═══════════════════════════════════════════════════════╗"
echo "║     PKG-BASH Launcher 构建监控 (SSH 版本)              ║"
echo "╚═══════════════════════════════════════════════════════╝"
echo -e "${NC}"

# 创建临时目录
mkdir -p "$TEMP_DIR"

# 克隆仓库（浅克隆）
echo -e "${BLUE}正在克隆仓库...${NC}"
git clone --depth=1 --no-checkout "$REPO_URL" "$TEMP_DIR" 2>&1 | tail -3

cd "$TEMP_DIR"

# 获取 Actions 状态
echo ""
echo -e "${BLUE}获取构建状态...${NC}"

# 方法 1: 通过 GitHub CLI (如果安装)
if command -v gh &> /dev/null; then
    echo -e "${GREEN}使用 GitHub CLI 获取状态...${NC}"
    gh run list --repo "$REPO" --limit 5 2>&1
    exit 0
fi

# 方法 2: 通过 git 获取最近的 workflow 运行
# GitHub 不通过 git 协议暴露 Actions 信息，需要 API

# 方法 3: 提示用户访问网页
echo ""
echo -e "${YELLOW}GitHub Actions 状态需要通过 API 或网页查看${NC}"
echo ""
echo -e "${CYAN}请访问以下页面查看构建状态:${NC}"
echo "  https://github.com/${REPO}/actions"
echo ""

# 检查是否有构建运行
echo -e "${BLUE}检查 workflow 文件...${NC}"
if [ -f ".github/workflows/build.yml" ]; then
    echo -e "${GREEN}✓ workflow 文件存在${NC}"
    echo ""
    echo "workflow 配置:"
    head -20 .github/workflows/build.yml
else
    echo -e "${RED}✗ workflow 文件不存在${NC}"
fi

echo ""
echo -e "${YELLOW}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""
echo -e "${BLUE}下一步操作:${NC}"
echo ""
echo "1. 访问 https://github.com/${REPO}/actions"
echo "2. 确认 Actions 已启用（首次使用需要点击启用按钮）"
echo "3. 查看构建状态和日志"
echo ""
echo -e "${CYAN}或者配置 GITHUB_TOKEN 后运行监控脚本:${NC}"
echo "  export GITHUB_TOKEN=your_token_here"
echo "  ./tools/monitor-build.sh"
echo ""
echo "获取 Token: https://github.com/settings/tokens"
echo ""
