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

    const home_a = document.getElementById("home-a");
    const Left_Menu_1 = document.getElementById("Left_Menu_1");
    home_a.click();
    Left_Menu_1.style.backgroundColor = "#F5F5F5"
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
    // 创建 XMLHttpRequest 对象
    const xhr = new XMLHttpRequest();
    // 打开一个 GET 请求，这里假设后端有一个 /newPage 的接口返回页面内容
    xhr.open('GET', requestUrl+url, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // 获取 iframe 元素
            const iframe = document.getElementById('context-d');
            // 获取 iframe 的文档对象
            const iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
            // 将 AJAX 请求返回的内容设置到 iframe 的文档中
            iframeDoc.open();
            iframeDoc.write(xhr.responseText);
            iframeDoc.close();
        }
    };
    // 发送请求
    xhr.send();
}



const menu_div = document.querySelectorAll('.Left_Menu');
console.log(menu_div);
function removeBgColor(){
    menu_div.forEach(div => {
        div.style.backgroundColor = '';
    });
}
menu_div.forEach(div => {
    div.addEventListener('click', function (){
        removeBgColor();
        this.style.backgroundColor = '#F5F5F5';
    });
})
