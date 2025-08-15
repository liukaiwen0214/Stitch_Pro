<%--
  Created by IntelliJ IDEA.
  User: lkwyo
  Date: 2025/4/6
  Time: 下午4:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mainpagecontent.css">
<body>
<div id="container"></div>
<%--存放随机式神--%>
<div class="random_person" id="random_person">
    <div class="loader">
        <div data-glitch="正在加载随机式神......" class="glitch">正在加载随机式神......</div>
    </div>
    <%--通过接口获取随机的头像--%>
    <div id="personImg_div">
        <img id="personImg_img" alt="加载失败" src="" crossorigin="anonymous">
    </div>
    <%--通过接口获取随机名字--%>
    <div id="personName">
        <span>

        </span>
    </div>
    <div id="skills">
        <div id="skill1" class="skillimg">
            <img id="skill1image" class="skillimgs" src="" alt="加载失败" crossorigin="anonymous" title="" height="60" width="60">
        </div>
        <div id="skill2" class="skillimg">
            <img id="skill2image" class="skillimgs" src="" alt="加载失败" crossorigin="anonymous" title="" height="60" width="60">
        </div>
        <div id="skill3" class="skillimg">
            <img id="skill3image" class="skillimgs" src="" alt="加载失败" crossorigin="anonymous" title="" height="60" width="60">
        </div>
    </div>
    <div id="descriptive">
        <div class="tooltip">
            <span class="skill_descriptive">

            </span>
        </div>
        <div class="tooltip">
            <span class="skill_descriptive">

            </span>
        </div>
        <div class="tooltip">
            <span class="skill_descriptive">

            </span>
        </div>
    </div>
</div>
<div class="chat-container-wrapper">
    <div class="chat-container">
        <!-- 后台消息 -->
        <div class="message bot-message">
            <p>我是Ai助手，请输入你的问题。</p>
        </div>
        <!-- 用户消息 -->
        <div class="message user-message">
            <p>用户消息显示。</p>
        </div>
    </div>
    <div class="input-container">
        <label for="messageInput"></label><input type="text" id="messageInput" autocomplete="off"
                                                 placeholder="输入你的消息">
        <button id="sendButton">发送</button>
    </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/static/utils/echarts.min.js"></script>
<script type="module" src="${pageContext.request.contextPath}/static/js/global.js"></script>
<script type="module" src="${pageContext.request.contextPath}/static/js/mainmenu.js"></script>
<script type="module" src="${pageContext.request.contextPath}/static/js/mainpagecontent.js"></script>
</html>
