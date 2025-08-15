const contextPath = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl = '/' + contextPath;

const pageClick = document.querySelectorAll('.page-click');
const jspContent = document.getElementById('jspContent');

pageClick.forEach(pageclick => {
    if (pageclick instanceof HTMLElement) {

        pageclick.addEventListener('click', (e) => {
            const data_page = pageclick.getAttribute('data-page');
            console.info(data_page)
            fetch(requestUrl + "/" + data_page).then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP错误，状态码: ${response.status}`);
                }
                return response.text();
            }).then(htmlContent => {
                // 将获取的HTML内容设置到iframe中
                jspContent.srcdoc = htmlContent;
            }).catch(error => {
                // 显示错误信息
                console.error('加载JSP页面时出错:', error);
            });
        })
    }
});