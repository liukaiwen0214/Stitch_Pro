const contextPath = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl = '/' + contextPath;

//Star_Home.jsp中左边菜单栏的“主页”按钮-a
const zhuye = document.getElementById('zhuye');
//Star_Home.jsp中左边菜单栏的“退出”按钮
const logoutButton = document.querySelector('.success-button-main');
//Star_Home.jsp中左边菜单栏的所有按钮
const leftMenus = document.querySelectorAll('.Left_Menu');
//Star_Home.jsp中dropdown-content的li标签
const listItems = document.querySelectorAll('.dropdown-content li');
//Star_Home.jsp中右上角欢迎标签的关闭按钮
const error__close = document.querySelector(".error__close");
//Star_Home.jsp中右上角欢迎标签
const closeLogin = document.getElementById('closeLogin');
//获取子标签点击事件
const childskid = document.querySelectorAll('.childs-kid');
//定义canvas颜色
const rarityColors = {
    "N": "#bbe0ec", "R": "#76c33a", "SR": "#7900da", "SSR": "#fabe41", "SP": "#bd0000"
};

// 标志位，用于避免DOMContentLoaded事件中重复触发
let isZhuyeClickedOnLoad = false;

/**
 * 页面加载好dom后立马执行
 */
document.addEventListener('DOMContentLoaded', function () {
    if (zhuye && !isZhuyeClickedOnLoad) {
        zhuye.click();
        zhuye.style.backgroundColor = "#F5F5F5";
        isZhuyeClickedOnLoad = true;
    }
});

/**
 * logout()退出方法
 * if (logoutButton)监控退出按钮是否被点击
 *
 */
function logout() {
    fetch(requestUrl + '/UserAuth/logout', {method: 'POST'})
        .then(() => window.location.href = requestUrl + '/MainMenu/MainMenu');
}

//监控按钮是否被点击
if (logoutButton) {
    logoutButton.addEventListener('click', function () {
        // 调用 logout 方法
        logout();
    });
}

/**
 * 监控主页是否被点击，以及点击后的事件
 */
function handleZhuyeClick() {
    // console.info("zhuye click")
    // 创建 XMLHttpRequest 对象
    const xhr = new XMLHttpRequest();
    // 打开一个 GET 请求，这里假设后端有一个 /newPage 的接口返回页面内容
    xhr.open('GET', requestUrl + "/MainPage/MainPageContent", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // 获取 iframe 元素
            const iframe = document.getElementById('context-d');

            // 定义 iframe 加载事件处理函数
            const iframeLoadHandler = function () {
                const iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
                // 增加延迟以确保 iframe 内部的 DOM 已经加载完成
                setTimeout(() => {
                    const container = iframeDoc.getElementById('container');
                    const personImg = iframeDoc.getElementById('personImg_img');
                    const personName = iframeDoc.getElementById('personName');
                    const personDiv = iframeDoc.getElementById('personImg_div');
                    const skill1image = iframeDoc.getElementById('skill1image');
                    const skill2image = iframeDoc.getElementById('skill2image');
                    const skill3image = iframeDoc.getElementById('skill3image');
                    if (container) {
                        // console.log('成功获取到 container 元素:', container);
                        getcanvas(rarityColors, requestUrl, container);
                    } else {
                        console.log('未找到 container 元素');
                    }
                    if (personImg) {
                        // console.log('成功获取到 personImg_img 元素:', personImg);
                        fetchRandomGod(requestUrl, personDiv, personImg, personName, skill1image, skill2image, skill3image)
                            .then(() => {
                                const loader = iframeDoc.querySelector('.loader');
                                if (loader) {
                                    loader.style.transition = 'opacity 2s ease-out';
                                    loader.style.opacity = '0';
                                    setTimeout(() => {
                                        loader.style.display = 'none';
                                    }, 100);
                                }
                            })
                            .catch(error => {
                                console.error('fetchRandomGod 执行失败:', error);
                            });
                        // 在这里可以对 personImg_img 元素进行操作
                    } else {
                        console.log('未找到 personImg_img 元素，尝试使用 MutationObserver 监听');
                        const observer = new MutationObserver((mutationsList) => {
                            for (const mutation of mutationsList) {
                                if (mutation.type === 'childList') {
                                    const newPersonImg = iframeDoc.getElementById('personImg_img');
                                    const personName = iframeDoc.getElementById('personName');
                                    const personDiv = iframeDoc.getElementById('personImg_div');
                                    const skill1image = iframeDoc.getElementById('skill1image');
                                    const skill2image = iframeDoc.getElementById('skill2image');
                                    const skill3image = iframeDoc.getElementById('skill3image');
                                    if (newPersonImg) {
                                        console.log('通过 MutationObserver 成功获取到 personImg_img 元素:', newPersonImg);
                                        // 不再需要监听，断开连接
                                        observer.disconnect();
                                        // 在这里可以对 newPersonImg 元素进行操作
                                        fetchRandomGod(requestUrl, personDiv, newPersonImg, personName, skill1image, skill2image, skill3image)
                                            .then(() => {
                                                const loader = iframeDoc.querySelector('.loader');
                                                if (loader) {
                                                    loader.style.transition = 'opacity 2s ease-out';
                                                    loader.style.opacity = '0';
                                                    setTimeout(() => {
                                                        loader.style.display = 'none';
                                                    }, 100);
                                                }
                                            })
                                            .catch(error => {
                                                console.error('fetchRandomGod 执行失败:', error);
                                            });
                                    }
                                }
                            }
                        });
                        observer.observe(iframeDoc.body, {childList: true, subtree: true});
                    }
                }, 100); // 延迟 1000 毫秒，可根据实际情况调整

                // 移除 iframe 加载事件监听器，防止重复触发
                iframe.removeEventListener('load', iframeLoadHandler);
            };

            // 监听 iframe 的 load 事件，当 iframe 内页面加载完成时触发
            iframe.addEventListener('load', iframeLoadHandler);

            // 获取 iframe 的文档对象
            const iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
            // 将 AJAX 请求返回的内容设置到 iframe 的文档中
            iframeDoc.open();
            iframeDoc.write(xhr.responseText);
            iframeDoc.close();
        }
    };
    // 发送请求
    xhr.send();
    // console.info("zhuye send")
}

if (zhuye) {
    // 先移除可能存在的事件监听器
    zhuye.removeEventListener('click', handleZhuyeClick);
    zhuye.addEventListener("click", handleZhuyeClick);
}

/**
 * 当页面加载时判断登陆状态
 */
fetch(requestUrl + '/UserAuth/currentUser').then(response => response.json()).then(data => {
    if (data.code !== 200) {
        history.pushState({}, 'Home', requestUrl + '/');
        window.location.href = requestUrl + '/';
    }
});

/**
 * 随机式神展示方法
 */
function fetchRandomGod(requestUrl, personDiv, personImg, personName, skill1image, skill2image, skill3image) {
    return fetch(requestUrl + '/ShikigmainDisplay/randshikigma', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.info(data);
            personImg.src = data.godavatar;
            switch (data.god_rarity) {
                case 1:
                    personDiv.style.backgroundColor = "#bbe0ec";
                    break;
                case 2:
                    personDiv.style.backgroundColor = "#76c33a";
                    break;
                case 3:
                    personDiv.style.backgroundColor = "#7900da";
                    break;
                case 4:
                    personDiv.style.backgroundColor = "#fabe41";
                    break;
                case 5:
                    personDiv.style.backgroundColor = "#bd0000";
                    break;
                default:
                    personDiv.style.backgroundColor = "#ffffff";
            }
            personImg.title = data.god_name;
            personName.querySelector("span").textContent = data.god_name;
            skill1image.src = data.skill1icon;
            skill2image.src = data.skill2icon;
            skill3image.src = data.skill3icon;
            skill1image.title = data.skill1name;
            skill2image.title = data.skill2name;
            skill3image.title = data.skill3name;
        });
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
});

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
 * 主页的饼状图
 * @param rarityColors 颜色
 * @param requestUrl 接口链接
 * @param container 饼状图的位置
 */
function getcanvas(rarityColors, requestUrl, container) {
    fetch(requestUrl + '/ShikigmainDisplay/reckoning').then(response => response.json()).then(data => {
        const echartsData = data.map(item => ({
            value: item.count, name: item.rarity, itemStyle: {
                color: rarityColors[item.rarity]
            }
        }));
        if (container) {
            const myChart = echarts.init(container, null, {
                renderer: 'canvas', useDirtyRect: false
            });
            // const app = {};
            let option;
            option = {
                title: {
                    text: '式神数量统计', left: 'center'
                }, tooltip: {
                    trigger: 'item'
                }, legend: {
                    top: '5%', left: 'center'
                }, series: [{
                    name: '式神统计', type: 'pie', radius: ['40%', '70%'], avoidLabelOverlap: false, itemStyle: {
                        borderRadius: 10, borderColor: '#fff', borderWidth: 2
                    }, label: {
                        show: false, position: 'center'
                    }, emphasis: {
                        label: {
                            show: true, fontSize: 40, fontWeight: 'bold'
                        }
                    }, labelLine: {
                        show: false
                    }, data: echartsData
                }]
            };
            if (option && typeof option === 'object') {
                myChart.setOption(option);
            }
            // 添加点击事件
            myChart.on('click', function (params) {
                // params 包含了点击的数据项的相关信息
                const name = params.name;
                const value = params.value;
                console.log(`你点击了 ${name}，数量为 ${value}`);
                // 你可以在这里添加更复杂的逻辑，比如跳转到详情页等
                // 例如：window.location.href = `detail.html?name=${name}`;
            });
            window.addEventListener('resize', myChart.resize);
        } else {
            console.error("dom空")
        }
    })
}

/**
 * childs-kid的点击事件
 * 当点击时将context-d显示出来
 */
childskid.forEach(childonclick => {
    childonclick.addEventListener('click', function () {
        const url = childonclick.getAttribute("data-menu");
        // console.info(event)
        // 创建 XMLHttpRequest 对象
        const xhr = new XMLHttpRequest();
        // 打开一个 GET 请求，这里假设后端有一个 /newPage 的接口返回页面内容
        xhr.open('GET', requestUrl + "/" + url, true);
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
            }
        };
        // 发送请求
        xhr.send();
    })
})