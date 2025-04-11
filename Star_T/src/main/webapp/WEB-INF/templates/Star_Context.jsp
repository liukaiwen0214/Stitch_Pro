<%--
  Created by IntelliJ IDEA.
  User: lkwyo
  Date: 2025/4/6
  Time: 下午4:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/star-context-style.css">
<body>
<div id="container"></div>
<div class="chat-container-wrapper">
    <div class="chat-container">
        <!-- 后台消息 -->
        <div class="message bot-message">
            <p>这是后台消息。</p>
        </div>
        <!-- 用户消息 -->
        <div class="message user-message">
            <p>这是我的消息。</p>
        </div>
    </div>
    <div class="input-container">
        <label for="messageInput"></label><input type="text" id="messageInput" autocomplete="off" placeholder="输入你的消息">
        <button id="sendButton">发送</button>
    </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/static/utils/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/star-context.js"></script>
<script src="${pageContext.request.contextPath}/static/js/chats-scripts.js"></script>
</html>
