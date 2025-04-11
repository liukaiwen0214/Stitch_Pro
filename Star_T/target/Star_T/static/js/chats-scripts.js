const chatContainer = document.querySelector('.chat-container');
const messageInput = document.getElementById('messageInput');
const sendButton = document.getElementById('sendButton');
// 获取当前页面的上下文路径
const contextPath_chats = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl_chats = '/' + contextPath_chats;

sendButton.addEventListener('click', () => {
    const message = $('#messageInput').val();
    if (message.trim() !== '') {
        const data = {
            message: message
        };
        $.ajax({
            url: requestUrl_chats + '/chat',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: (response) => {
                const message = messageInput.value;
                if (message.trim() !== '') {
                    const userMessageDiv = document.createElement('div');
                    userMessageDiv.classList.add('message', 'user-message');
                    userMessageDiv.innerHTML = `<p class="userp">${message}</p>`;
                    chatContainer.appendChild(userMessageDiv);
                }
                messageInput.value = '';
                // 模拟后台回复
                setTimeout(() => {
                    const botMessageDiv = document.createElement('div');
                    botMessageDiv.classList.add('message', 'bot-message');
                    let resultHtml = '';
                    // 遍历 Map 中的键值对
                    for (const [key, value] of Object.entries(response)) {
                        botMessageDiv.innerHTML = `<p class="aip">${key}: ${value}</p>`;
                    }
                    chatContainer.appendChild(botMessageDiv);
                    // 调用滚动函数
                    scrollToBottomSlowly(chatContainer);
                }, 1000);

            },
            error: (error) => {
                $('#result').html(`Error: ${error.status}`);
            }
        });
    }
});
// 缓慢滚动到容器底部的函数
function scrollToBottomSlowly(element) {
    const targetScrollTop = element.scrollHeight - element.clientHeight;
    const currentScrollTop = element.scrollTop;
    const distance = targetScrollTop - currentScrollTop;
    const step = 10; // 每次滚动的步长
    const interval = 20; // 滚动间隔时间（毫秒）

    let scrollPosition = currentScrollTop;
    const scrollInterval = setInterval(() => {
        if (scrollPosition < targetScrollTop) {
            scrollPosition += step;
            if (scrollPosition > targetScrollTop) {
                scrollPosition = targetScrollTop;
            }
            element.scrollTop = scrollPosition;
        } else {
            clearInterval(scrollInterval);
        }
    }, interval);
}
