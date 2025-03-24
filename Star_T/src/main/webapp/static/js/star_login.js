// 获取当前页面的上下文路径
const contextPath = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl = '/' + contextPath

// 绑定表单提交事件
$('#loginForm').submit(function (event) {
    // 阻止表单默认提交行为
    event.preventDefault();
    // 发送Ajax请求
    $.ajax({
        url: requestUrl + '/login', // 后端接口URL
        type: 'POST',
        dataType: 'json',
        data: $(this).serialize(), // 序列化表单数据
        success: function (result) {
            if (result.success) {
                // 登录成功，跳转到主页
                window.location.href = requestUrl+result.redirectUrl;

            } else {
                console.log(result);
            }
        },
        error: function (xhr, status, error) {
            // 处理请求错误
            console.log('请求失败: ' + error);
        }
    });
});