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
                    userMessageDiv.innerHTML = `<p>${message}</p>`;
                    chatContainer.appendChild(userMessageDiv);
                }
                // 模拟后台回复
                setTimeout(() => {
                    const botMessageDiv = document.createElement('div');
                    botMessageDiv.classList.add('message', 'bot-message');
                    let resultHtml = '';
                    // 遍历 Map 中的键值对
                    for (const [key, value] of Object.entries(response)) {
                        botMessageDiv.innerHTML = `<p>${key}: ${value}</p>`;
                    }
                    chatContainer.appendChild(botMessageDiv);
                }, 1000);
                messageInput.value = '';
            },
            error: (error) => {
                $('#result').html(`Error: ${error.status}`);
            }
        });
    }
});



