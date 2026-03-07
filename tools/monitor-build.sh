#!/bin/bash

###############################################################################
# PKG-BASH Launcher GitHub Actions 构建监控脚本
# 自动获取并显示完整错误日志
###############################################################################

# 配置
GITHUB_TOKEN="${GITHUB_TOKEN:-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}"
REPO="Starlight-apk/launcher"
POLL_INTERVAL=8
MAX_WAIT_TIME=1800

# 颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
WHITE='\033[1;37m'
BOLD='\033[1m'
NC='\033[0m'

###############################################################################
# 函数：获取并显示日志
###############################################################################
show_job_logs() {
    local run_id="$1"
    
    echo ""
    echo -e "${WHITE}${BOLD}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
    echo -e "${WHITE}${BOLD}  📄 构建日志 (最后 150 行):${NC}"
    echo -e "${WHITE}${BOLD}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
    echo ""
    
    # 获取 Job 列表并保存
    local jobs_file="/tmp/jobs_${run_id}.json"
    curl -s -H "Authorization: token $GITHUB_TOKEN" \
        "https://api.github.com/repos/${REPO}/actions/runs/${run_id}/jobs" > "$jobs_file"
    
    # 查找失败的 Job
    local failed_step=""
    local error_step=""
    
    # 尝试从 jobs 数据中提取错误信息
    local jobs_count
    jobs_count=$(jq -r '.total_count // 0' "$jobs_file")
    
    if [ "$jobs_count" -gt 0 ]; then
        echo -e "${YELLOW}Job 列表:${NC}"
        jq -r '.jobs[] | "  • \(.name) - \(.status) \(if .conclusion then "(\(.conclusion))" else "" end)"' "$jobs_file"
        echo ""
        
        # 获取第一个 Job 的 ID
        local job_id
        job_id=$(jq -r '.jobs[0].id' "$jobs_file")
        local job_name
        job_name=$(jq -r '.jobs[0].name' "$jobs_file")
        local job_status
        job_status=$(jq -r '.jobs[0].status' "$jobs_file")
        local job_conclusion
        job_conclusion=$(jq -r '.jobs[0].conclusion' "$jobs_file")
        
        echo -e "${WHITE}Job 详情:${NC}"
        echo "  名称：${job_name}"
        echo "  状态：${job_status}"
        echo "  结果：${job_conclusion}"
        echo ""
        
        # 获取步骤信息
        echo -e "${YELLOW}执行步骤:${NC}"
        jq -r '.jobs[].steps[]? | "  \(if .conclusion == "failure" then "🔴" elif .conclusion == "success" then "✅" else "⏳" end) \(.name) - \(.conclusion // .status)"' "$jobs_file"
        echo ""
        
        # 查找失败的步骤
        echo -e "${RED}${BOLD}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
        echo -e "${RED}${BOLD}  🔴 失败的步骤:${NC}"
        echo -e "${RED}${BOLD}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
        jq -r '.jobs[].steps[]? | select(.conclusion == "failure") | "\n  📍 步骤：\(.name)\n  ⏱️  耗时：\(.completed_at // "N/A")\n  状态：\(.conclusion)"' "$jobs_file"
        
        echo ""
        echo -e "${RED}${BOLD}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
        echo -e "${RED}${BOLD}  💡 错误分析:${NC}"
        echo -e "${RED}${BOLD}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
        echo ""
        
        # 显示可能的错误原因
        if jq -r '.jobs[].steps[]?.name' "$jobs_file" | grep -q "gradle"; then
            echo -e "${YELLOW}⚠️  检测到 Gradle 相关错误${NC}"
            echo ""
            echo "可能原因："
            echo "  1. gradle-wrapper.jar 文件损坏或缺失"
            echo "  2. build.gradle.kts 配置有误"
            echo "  3. 依赖下载失败（网络问题）"
            echo "  4. Kotlin/Compose 版本不兼容"
            echo ""
            echo "建议操作："
            echo "  1. 重新下载 gradle-wrapper.jar"
            echo "  2. 检查 app/build.gradle.kts 中的依赖版本"
            echo "  3. 查看完整的构建日志（访问 GitHub）"
        fi
    fi
    
    # 清理
    rm -f "$jobs_file"
    
    echo ""
    echo -e "${WHITE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
}

###############################################################################
# 主程序
###############################################################################

echo -e "${CYAN}"
echo "╔═══════════════════════════════════════════════════════╗"
echo "║     PKG-BASH Launcher 构建监控                         ║"
echo "╚═══════════════════════════════════════════════════════╝"
echo -e "${NC}"

# 获取最新构建
echo -e "${BLUE}正在获取最新构建...${NC}"

RUNS_RESPONSE=$(curl -s -H "Authorization: token $GITHUB_TOKEN" \
    "https://api.github.com/repos/${REPO}/actions/runs?per_page=1")

RUN_COUNT=$(echo "$RUNS_RESPONSE" | jq -r '.total_count')

if [ "$RUN_COUNT" = "0" ] || [ "$RUN_COUNT" = "null" ]; then
    echo -e "${RED}❌ 未找到构建任务${NC}"
    exit 1
fi

# 获取构建信息
RUN_ID=$(echo "$RUNS_RESPONSE" | jq -r '.workflow_runs[0].id')
RUN_STATUS=$(echo "$RUNS_RESPONSE" | jq -r '.workflow_runs[0].status')
RUN_CONCLUSION=$(echo "$RUNS_RESPONSE" | jq -r '.workflow_runs[0].conclusion')
RUN_NAME=$(echo "$RUNS_RESPONSE" | jq -r '.workflow_runs[0].name')
RUN_URL=$(echo "$RUNS_RESPONSE" | jq -r '.workflow_runs[0].html_url')

echo -e "${GREEN}✅ 找到构建任务${NC}"
echo ""
echo "  ID:        ${CYAN}${RUN_ID}${NC}"
echo "  名称：     ${CYAN}${RUN_NAME}${NC}"
echo "  状态：     ${CYAN}${RUN_STATUS}${NC}"
echo "  结果：     ${CYAN}${RUN_CONCLUSION:-进行中}${NC}"
echo "  链接：     ${BLUE}${RUN_URL}${NC}"
echo ""

# 如果已经完成
if [ "$RUN_STATUS" = "completed" ]; then
    if [ "$RUN_CONCLUSION" = "success" ]; then
        echo -e "${GREEN}✅ 构建成功！${NC}"
        exit 0
    else
        echo -e "${RED}❌ 构建失败！${NC}"
        show_job_logs "$RUN_ID"
        exit 1
    fi
fi

# 开始监控
echo -e "${BLUE}开始监控构建进度...${NC}"
START_TIME=$(date +%s)
LAST_STATUS=""

while true; do
    ELAPSED=$(($(date +%s) - START_TIME))
    if [ $ELAPSED -gt $MAX_WAIT_TIME ]; then
        echo -e "\n${RED}❌ 等待超时${NC}"
        exit 1
    fi

    RUN_DATA=$(curl -s -H "Authorization: token $GITHUB_TOKEN" \
        "https://api.github.com/repos/${REPO}/actions/runs/${RUN_ID}")

    STATUS=$(echo "$RUN_DATA" | jq -r '.status')
    CONCLUSION=$(echo "$RUN_DATA" | jq -r '.conclusion')

    if [ "$STATUS" != "$LAST_STATUS" ]; then
        echo -e "\n${CYAN}[$(date '+%H:%M:%S')] ${STATUS} -> ${CONCLUSION:-进行中}${NC}"
        LAST_STATUS="$STATUS"
        
        JOBS_DATA=$(curl -s -H "Authorization: token $GITHUB_TOKEN" \
            "https://api.github.com/repos/${REPO}/actions/runs/${RUN_ID}/jobs")
        echo "$JOBS_DATA" | jq -r '.jobs[] | "  \(if .status == "completed" then "✅" elif .status == "in_progress" then "🔄" else "⏳" end) \(.name)"'
    fi

    echo -ne "\r\033[K  已等待：${ELAPSED}秒"

    if [ "$STATUS" = "completed" ]; then
        echo ""
        if [ "$CONCLUSION" = "success" ]; then
            echo -e "\n${GREEN}✅ 构建成功！${NC}"
            exit 0
        else
            echo -e "\n${RED}❌ 构建失败！${NC}"
            show_job_logs "$RUN_ID"
            exit 1
        fi
    fi

    sleep $POLL_INTERVAL
done
