document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // 此处添加登录逻辑
    console.log('Email:', email);
    console.log('Password:', password);
});