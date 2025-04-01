fetch('/currentUser')
    .then(response => response.json())
    .then(data => {
        if(data.code === 200) {
            document.getElementById('welcome').innerText = `欢迎 ${data.data}`;
        } else {
            window.location.href = '/Star_Login.jsp';
        }
    });

// 退出登录
function logout() {
    fetch('/logout', {method: 'POST'})
        .then(() => window.location.href = '/Star_Login.jsp');
}