// 监听 popstate 事件
window.addEventListener('popstate', function (e) {
    const currentPath = window.location.pathname;
    const targetPath = new URL(document.referrer).pathname;
    if (targetPath.includes(`${requestUrl}/login`)) {
        // 阻止默认的返回行为
        e.preventDefault();
        // 可以选择重新将当前页面状态压入历史记录，避免历史记录错乱
        history.pushState({}, '', currentPath);
    }
});
