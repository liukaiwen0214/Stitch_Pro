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
