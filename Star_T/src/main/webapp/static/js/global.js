// context_path_check.js
// 检查全局作用域下是否已经存在 contextPath
if (typeof window.contextPath === 'undefined') {
    // 获取当前页面的上下文路径
    window.contextPath = window.location.pathname.split('/')[1];
}

// 检查全局作用域下是否已经存在 requestUrl
if (typeof window.requestUrl === 'undefined') {
    // 拼接完整的请求URL
    window.requestUrl = '/' + window.contextPath;
}
// 监听 popstate 事件
window.addEventListener('popstate', function (e) {
    const currentPath = window.location.pathname;
    const targetPath = new URL(document.referrer).pathname;
    if (targetPath.includes(`${requestUrl}/UserAuth/login`)) {
        // 阻止默认的返回行为
        e.preventDefault();
        // 可以选择重新将当前页面状态压入历史记录，避免历史记录错乱
        history.pushState({}, '', currentPath);
    }
});
