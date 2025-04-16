// 获取当前页面的上下文路径
import {getGodcount} from "./star-context.js";

const contextPath = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl = '/' + contextPath;
/**
 * 获取按钮ID
 * @type {HTMLElement}
 */
//Star_Home.jsp中左边菜单栏的“主页”按钮-a
const zhuye = document.getElementById('zhuye');
//Star_Home.jsp中左边菜单栏的“退出”按钮
const logoutButton = document.querySelector('.success-button-main');
//Star_Home.jsp中左边菜单栏的所有按钮
const leftMenus = document.querySelectorAll('.Left_Menu');
//Star_Home.jsp中dropdown-content的li标签
const listItems = document.querySelectorAll('.dropdown-content li');
//Star_Home.jsp中右上角欢迎标签的关闭按钮
const error__close = document.querySelector(".error__close")
//Star_Home.jsp中右上角欢迎标签
const closeLogin = document.getElementById('closeLogin');


//定义canvas颜色
const rarityColors = {
    "N": "#bbe0ec", "R": "#76c33a", "SR": "#7900da", "SSR": "#fabe41", "SP": "#bd0000"
};

/**
 * 页面加载好dom后立马执行
 */
document.addEventListener('DOMContentLoaded', function () {
    if (zhuye) {
        // zhuye.click();
        zhuye.style.backgroundColor = "#F5F5F5"
    }
});

/**
 * logout()退出方法
 * if (logoutButton)监控退出按钮是否被点击
 *
 */
function logout() {
    fetch(requestUrl + '/logout', {method: 'POST'})
        .then(() => window.location.href = requestUrl + '/Star_Home');
}
//监控按钮是否被点击
if (logoutButton) {
    logoutButton.addEventListener('click', function () {
        // 调用 logout 方法
        logout();
    });
}
/**
 *监控主页是否被点击，以及点击后的事件
 */
if (zhuye) {
    zhuye.addEventListener("click", function () {
        // 创建 XMLHttpRequest 对象
        const xhr = new XMLHttpRequest();
        // 打开一个 GET 请求，这里假设后端有一个 /newPage 的接口返回页面内容
        xhr.open('GET', requestUrl + "/home_context", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // 获取 iframe 元素
                const iframe = document.getElementById('context-d');
                // 获取 iframe 的文档对象
                const iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
                // 将 AJAX 请求返回的内容设置到 iframe 的文档中
                iframeDoc.open();
                iframeDoc.write(xhr.responseText);
                iframeDoc.close();
                getGodcount();
            }
        };
        // 发送请求
        xhr.send();
    })

}
/**
 * 当页面加载时判断登陆状态
 */
fetch(requestUrl + '/currentUser').then(response => response.json()).then(data => {
    if (data.code === 200) {
        if (document.getElementById('welcome')) {
            document.getElementById('welcome').innerText = `登陆成功 ${data.data}`;
        }
    } else {
        history.pushState({}, 'Home', requestUrl + '/')
        window.location.href = requestUrl + '/';
    }
});

/**
 * 右上角欢迎菜单的隐藏方法
 */
if (error__close) {
    error__close.addEventListener("click", function () {
        closeLogin.style.display = 'none';
    })
}

/**
 * 定义查询式神方法
 * @returns {Promise<any|null>} 返回一个式神
 */
export async function fetchRandomGod() {
    try {
        const response = await fetch(requestUrl + '/getRandomGod', {method: 'GET'});
        if (!response.ok) {
            console.log("获取错误！状态码:" + response.status);
        }
        return await response.json();
    } catch (error) {
        console.error('请求出错:', error);
        return null;
    }
}

/**
 * 遍历左边菜单栏 添加点击时的样式
 */
leftMenus.forEach(leftMenu => {
    leftMenu.addEventListener('click', function (event) {
        event.preventDefault();
        const parentA = this.closest('a');
        const currentDropdown = parentA.querySelector('.dropdown-content');
        // 先隐藏所有的 dropdown-content
        const allDropdowns = document.querySelectorAll('.dropdown-content');
        allDropdowns.forEach(dropdown => {
            if (dropdown !== currentDropdown) {
                dropdown.style.display = 'none';
            }
        });
        // 切换当前点击的 Left_Menu 对应的 dropdown-content 的显示状态
        if (currentDropdown) {
            if (currentDropdown.style.display === 'none' || currentDropdown.style.display === '') {
                currentDropdown.style.display = 'block';
            } else {
                currentDropdown.style.display = 'none';
            }
        }
    });
});

function removeBgColor() {
    leftMenus.forEach(div => {
        div.style.backgroundColor = '';
    });
}

/**
 * 遍历左边菜单栏 添加点击时的样式
 */
leftMenus.forEach(div => {
    div.addEventListener('click', function () {
        removeBgColor();
        this.style.backgroundColor = '#F5F5F5';
    });
})
/**
 * 遍历所有的li标签，增加点击时的样式
 */
listItems.forEach(item => {
    item.addEventListener('click', function () {
        // 移除所有 li 的选中状态
        const allListItems = document.querySelectorAll('.dropdown-content li');
        allListItems.forEach(li => {
            li.classList.remove('selected');
        });
        // 添加当前点击 li 的选中状态
        this.classList.add('selected');
    });
});

/**
 * 创建canvas方法
 */
fetchRandomGod().then(respone => {
    const img_url = document.getElementById("personImg_img");
    if (img_url) {
        img_url.src = requestUrl + '/static/image/godAvatar/' + respone.id + '.png'
    }
})




