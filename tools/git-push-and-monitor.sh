#!/bin/bash

###############################################################################
# PKG-BASH Launcher Git 推送 + 自动监控脚本
# 
# 使用方法：
#   ./tools/git-push-and-monitor.sh
#   ./tools/git-push-and-monitor.sh "提交信息"
#
# 功能：
#   1. 自动提交更改
#   2. 推送到 GitHub
#   3. 自动启动构建监控
###############################################################################

set -e

# 配置
REPO_REMOTE="${GITHUB_REMOTE:-origin}"
REPO_BRANCH="${GITHUB_BRANCH:-main}"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
MONITOR_SCRIPT="${SCRIPT_DIR}/monitor-build.sh"

# 颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

###############################################################################
# 工具函数
###############################################################################

log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_header() {
    echo ""
    echo -e "${CYAN}════════════════════════════════════════════════════${NC}"
    echo -e "${CYAN}  $1${NC}"
    echo -e "${CYAN}════════════════════════════════════════════════════${NC}"
    echo ""
}

###############################################################################
# 检查函数
###############################################################################

check_git() {
    if ! command -v git &> /dev/null; then
        log_error "未找到 git 命令"
        exit 1
    fi
    
    if ! git rev-parse --git-dir > /dev/null 2>&1; then
        log_error "当前目录不是 Git 仓库"
        exit 1
    fi
    
    log_info "Git 检查通过"
}

check_monitor_script() {
    if [ ! -f "$MONITOR_SCRIPT" ]; then
        log_error "监控脚本不存在：$MONITOR_SCRIPT"
        exit 1
    fi
    
    chmod +x "$MONITOR_SCRIPT"
    log_info "监控脚本已准备"
}

check_github_token() {
    if [ -n "$GITHUB_TOKEN" ]; then
        log_info "GitHub Token 已配置 (API 限流更高)"
    else
        log_warning "未配置 GITHUB_TOKEN"
        log_info "如需更高 API 限流，请设置环境变量:"
        echo "  export GITHUB_TOKEN=your_token_here"
    fi
}

###############################################################################
# Git 操作
###############################################################################

git_status() {
    print_header "Git 状态"
    
    git status --short
    
    local changed=$(git status --porcelain | wc -l)
    
    if [ "$changed" -eq 0 ]; then
        log_warning "没有更改需要提交"
        echo ""
        echo "工作区干净，无需推送"
        echo ""
        echo "如需强制推送，请运行:"
        echo "  git push $REPO_REMOTE $REPO_BRANCH"
        echo ""
        echo "然后运行监控脚本:"
        echo "  $MONITOR_SCRIPT"
        echo ""
        exit 0
    fi
    
    log_info "检测到 $changed 个更改"
}

git_commit() {
    local commit_message="$1"
    
    if [ -z "$commit_message" ]; then
        commit_message="chore: 自动提交 $(date '+%Y-%m-%d %H:%M:%S')"
    fi
    
    print_header "提交更改"
    
    log_info "提交信息：$commit_message"
    
    git add -A
    git commit -m "$commit_message"
    
    log_success "提交完成"
}

git_push() {
    print_header "推送到 GitHub"
    
    log_info "远程：$REPO_REMOTE"
    log_info "分支：$REPO_BRANCH"
    
    if git push "$REPO_REMOTE" "$REPO_BRANCH"; then
        log_success "推送成功"
    else
        log_error "推送失败"
        echo ""
        echo "可能原因："
        echo "  1. 网络连接问题"
        echo "  2. SSH 密钥未配置"
        echo "  3. 仓库权限不足"
        echo ""
        echo "请检查 SSH 配置:"
        echo "  ssh -T git@github.com"
        echo ""
        exit 1
    fi
}

###############################################################################
# 主流程
###############################################################################

main() {
    local commit_message="$1"
    
    echo -e "${CYAN}"
    echo "╔═══════════════════════════════════════════════════╗"
    echo "║                                                   ║"
    echo "║     PKG-BASH Launcher 推送 + 监控一体化脚本       ║"
    echo "║                                                   ║"
    echo "╚═══════════════════════════════════════════════════╝"
    echo -e "${NC}"
    
    # 检查
    check_git
    check_monitor_script
    check_github_token
    
    # Git 状态
    git_status
    
    # 提交
    git_commit "$commit_message"
    
    # 推送
    git_push
    
    # 等待 GitHub 接收
    echo ""
    log_info "等待 GitHub 接收推送..."
    sleep 3
    
    # 启动监控
    print_header "启动构建监控"
    
    log_info "启动监控脚本：$MONITOR_SCRIPT"
    echo ""
    
    # 执行监控脚本
    chmod +x "$MONITOR_SCRIPT"
    "$MONITOR_SCRIPT"
    
    # 监控结果
    local exit_code=$?
    
    echo ""
    if [ $exit_code -eq 0 ]; then
        print_header "完成"
        log_success "推送和构建全部完成! 🎉"
    else
        print_header "构建失败"
        log_error "构建失败，请检查错误日志"
    fi
    
    exit $exit_code
}

# 显示帮助
show_help() {
    echo "用法：$0 [选项] [提交信息]"
    echo ""
    echo "选项:"
    echo "  -h, --help      显示帮助"
    echo "  -n, --no-monitor 只推送，不监控"
    echo ""
    echo "示例:"
    echo "  $0                           # 使用自动提交信息"
    echo "  $0 \"修复 bug\"                 # 自定义提交信息"
    echo "  $0 -n                        # 只推送不监控"
    echo ""
    echo "环境变量:"
    echo "  GITHUB_TOKEN    GitHub API Token (可选)"
    echo "  GITHUB_REMOTE   远程仓库名 (默认：origin)"
    echo "  GITHUB_BRANCH   分支名 (默认：main)"
    echo ""
}

# 解析参数
NO_MONITOR=false

while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            show_help
            exit 0
            ;;
        -n|--no-monitor)
            NO_MONITOR=true
            shift
            ;;
        *)
            COMMIT_MESSAGE="$1"
            shift
            ;;
    esac
done

# 如果不监控，直接推送
if [ "$NO_MONITOR" = true ]; then
    check_git
    
    git_status
    git_commit "$COMMIT_MESSAGE"
    git_push
    
    echo ""
    log_success "推送完成!"
    echo ""
    echo "构建已在后台运行，访问查看:"
    echo "  https://github.com/Starlight-apk/launcher/actions"
    echo ""
    echo "如需监控构建，请运行:"
    echo "  $MONITOR_SCRIPT"
    echo ""
    exit 0
fi

# 正常运行
main "$COMMIT_MESSAGE"
