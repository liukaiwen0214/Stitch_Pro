<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/star_Home.css">
<body>

<h1 id="welcome">正在加载用户信息...</h1>
<button onclick="logout()">退出登录</button>
<div id="Bast_1">
    <div id="Left_Bast_1">
        <div class="Left_Menu" id="Left_Menu_1">
<span>ceshi</span>
        </div>
        <div class="Left_Menu" id="Left_Menu_2">

        </div>
        <div class="Left_Menu" id="Left_Menu_3">

        </div>
        <div class="Left_Menu" id="Left_Menu_4">

        </div>
        <div class="Left_Menu" id="Left_Menu_5">

        </div>
        <div class="Left_Menu" id="Left_Menu_6">

        </div>

        <div id="Top_Bast_1">
            <div>

            </div>
        </div>
    </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/star_login.js"></script>
<script>
    // 页面加载时检查登录状态
    fetch('/currentUser')
        .then(response => response.json())
        .then(data => {
            if(data.code === 200) {
                <%--document.getElementById('welcome').innerText = `欢迎 ${data.data}`;--%>
            } else {
                window.location.href = '/Star_Login.jsp';
            }
        });

    // 退出登录
    function logout() {
        fetch('/logout', {method: 'POST'})
            .then(() => window.location.href = '/Star_Login.jsp');
    }
</script>
</html>
