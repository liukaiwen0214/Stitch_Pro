// 获取当前页面的上下文路径
const contextPath = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl = '/' + contextPath;

document.getElementById('loginForm').onsubmit = function(e) {
    e.preventDefault();
    const formData = new FormData(this);

    // 显示进度条
    const progressBar = document.getElementById('progress-loader');
    const progress = document.getElementById('progress');
    progressBar.style.display = 'block';
    let width = 0;
    const interval = setInterval(() => {
        if (width >= 100) {
            clearInterval(interval);
            // 进度条完成后跳转到主页
            window.location.href = requestUrl + '/Star_Home';
        } else {
            width++;
            progress.style.width = width + '%';
        }
    }, 20); // 30ms 一次更新，共 3000ms（3 秒）

    // 发送登录请求
    fetch(requestUrl + '/login', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.code !== 200) {
                // 登录失败，清除进度条并显示错误信息
                clearInterval(interval);
                progressBar.style.display = 'none';
                progress.style.width = '0%';
                console.error(data.message);
            }
        });
};