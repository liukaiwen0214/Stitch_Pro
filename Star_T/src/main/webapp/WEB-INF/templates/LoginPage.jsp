<%--
  Created by IntelliJ IDEA.
  User: lkwyo
  Date: 2025/3/22
  Time: 下午6:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/loginpage.css">
</head>
<body>
<div class="login-container">
    <div class="logo">
        <div class="login_logo"></div>
    </div>
    <div id="progress-loader" class="progress-loader">
        <div id="progress" class="progress"></div>
    </div>

    <h1>欢迎回来</h1>

    <form id="loginForm">
        <div class="form-group">
            <label for="email"></label>
            <input type="text" id="email" name="email" placeholder="请输入账户/用户名/手机号" required>
        </div>

        <div class="form-group">
            <label for="password"></label>
            <input type="password" id="password" name="password" placeholder="请输入密码" required>
        </div>

        <div class="forgot-password">
            <a href="#">忘记密码？</a>
        </div>
        <button type="submit">登录</button>
    </form>

    <div class="signup-link">
        没有账户？ <a href="#">立即注册</a>
    </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/loginpage.js"></script>
<script src="${pageContext.request.contextPath}/static/js/global.js"></script>
</html>
