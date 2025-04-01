// function login() {
//     // 显示进度条
//     document.getElementById('progress-bar').style.display = 'block';
//     var progress = 0;
//     var interval = setInterval(function () {
//         if (progress < 100) {
//             progress += 10;
//             document.getElementById('progress').style.width = progress + '%';
//         } else {
//             clearInterval(interval);
//         }
//     }, 100);
//     // 后续 AJAX 请求代码保持不变
// }