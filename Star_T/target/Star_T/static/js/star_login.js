// 获取当前页面的上下文路径
const contextPath = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl = '/' + contextPath

document.getElementById('loginForm').onsubmit = function(e) {
    console.log(requestUrl);
    e.preventDefault();
    const formData = new FormData(this);
    fetch(requestUrl+'/login', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                window.location.href = requestUrl+data.redirectUrl;
            } else {
                console.error(data.message);
            }
        });
};
