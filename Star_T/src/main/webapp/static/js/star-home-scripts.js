// 获取当前页面的上下文路径
const contextPath = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl = '/' + contextPath

fetch(requestUrl + '/currentUser')
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) {
            document.getElementById('welcome').innerText = `登陆成功 ${data.data}`;
        } else {
            history.pushState({}, 'Home', requestUrl + '/')
            window.location.href = requestUrl + '/';
        }
    });


document.addEventListener('DOMContentLoaded', function () {
    const logoutButton = document.querySelector('.success-button-main');
    if (logoutButton) {
        logoutButton.addEventListener('click', function () {
            // 调用 logout 方法
            logout();
        });
    }
    document.getElementById("home-a").click();
});


// 退出登录
function logout() {
    fetch(requestUrl + '/logout', {method: 'POST'})
        .then(() =>
            window.location.href = requestUrl + '/Star_Home' );
}

function welcome_close() {
    const closeLogin = document.getElementById('closeLogin');
    closeLogin.style.display = 'none';
}

// 使用 AJAX 加载页面内容
function topUrl(url) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', requestUrl+url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('context-d').innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

