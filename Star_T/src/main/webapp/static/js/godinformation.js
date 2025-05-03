// 模拟从 JSON 文件获取式神数据
const shikigamiData = [
    {
        name: "茨木童子",
        avatar: "https://picsum.photos/200/200",
        rarity: "SSR"
    },
    {
        name: "酒吞童子",
        avatar: "https://picsum.photos/200/201",
        rarity: "SSR"
    },
    {
        name: "大天狗",
        avatar: "https://picsum.photos/200/202",
        rarity: "SSR"
    },
    {
        name: "荒川之主",
        avatar: "https://picsum.photos/200/203",
        rarity: "SSR"
    },
    {
        name: "妖刀姬",
        avatar: "https://picsum.photos/200/204",
        rarity: "SSR"
    },
    {
        name: "青行灯",
        avatar: "https://picsum.photos/200/205",
        rarity: "SSR"
    },
    {
        name: "花鸟卷",
        avatar: "https://picsum.photos/200/206",
        rarity: "SSR"
    },
    {
        name: "小鹿男",
        avatar: "https://picsum.photos/200/207",
        rarity: "SSR"
    },
    {
        name: "座敷童子",
        avatar: "https://picsum.photos/200/208",
        rarity: "R"
    }
];

// 获取容器元素
const shikigamiContainer = document.querySelector('.grid');
const filterButtons = document.querySelectorAll('[data-filter]');
const searchInput = document.getElementById('search-input');

// 渲染式神卡片的函数
function renderShikigami(filter = 'all', searchQuery = '') {
    shikigamiContainer.innerHTML = '';
    let filteredData = shikigamiData;
    if (filter!== 'all') {
        filteredData = filteredData.filter(shikigami => shikigami.rarity === filter);
    }
    if (searchQuery) {
        filteredData = filteredData.filter(shikigami => shikigami.name.includes(searchQuery));
    }
    filteredData.forEach(shikigami => {
        const card = document.createElement('div');
        card.classList.add('bg-white', 'rounded-lg', 'shadow-md', 'overflow-hidden', 'transition-transform', 'hover:scale-105');

        const avatar = document.createElement('img');
        avatar.src = shikigami.avatar;
        avatar.alt = shikigami.name;
        avatar.classList.add('w-full', 'h-48', 'object-cover');

        const info = document.createElement('div');
        info.classList.add('p-4');

        const name = document.createElement('h3');
        name.textContent = shikigami.name;
        name.classList.add('text-xl', 'font-bold', 'text-gray-800', 'mb-2');

        const rarity = document.createElement('p');
        rarity.textContent = `稀有度: ${shikigami.rarity}`;
        rarity.classList.add('text-gray-600');

        info.appendChild(name);
        info.appendChild(rarity);

        card.appendChild(avatar);
        card.appendChild(info);

        shikigamiContainer.appendChild(card);
    });
}

// 初始渲染
renderShikigami();

// 监听筛选按钮点击事件
filterButtons.forEach(button => {
    button.addEventListener('click', function () {
        filterButtons.forEach(btn => btn.classList.remove('active'));
        this.classList.add('active');
        const selectedFilter = this.dataset.filter;
        const searchQuery = searchInput.value;
        renderShikigami(selectedFilter, searchQuery);
    });
});

// 监听搜索框输入事件
searchInput.addEventListener('input', function () {
    const searchQuery = this.value;
    const activeFilterButton = document.querySelector('.active');
    const selectedFilter = activeFilterButton.dataset.filter;
    renderShikigami(selectedFilter, searchQuery);
});