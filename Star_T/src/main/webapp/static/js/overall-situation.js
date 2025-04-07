// 获取当前页面的上下文路径
const contextPath_overall = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl_overall = '/' + contextPath_overall;

// 监听 popstate 事件
window.addEventListener('popstate', function (e) {
    const currentPath = window.location.pathname;
    const targetPath = new URL(document.referrer).pathname;
    if (targetPath.includes(`${requestUrl_overall}/login`)) {
        // 阻止默认的返回行为
        e.preventDefault();
        // 可以选择重新将当前页面状态压入历史记录，避免历史记录错乱
        history.pushState({}, '', currentPath);
    }
});
