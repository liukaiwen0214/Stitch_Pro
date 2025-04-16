const rarityColors = {
    "N": "#bbe0ec", "R": "#76c33a", "SR": "#7900da", "SSR": "#fabe41", "SP": "#bd0000"
};
const dom = document.getElementById('container');

// 使用 fetch API 获取列表数据
export function getGodcount() {
    fetch(requestUrl + '/getGodCount').then(response => response.json()).then(data => {
        console.info("当前方法名字是：getGodcount；----star-context.js")
        console.log(data);
        const echartsData = data.map(item => ({
            value: item.count, name: item.rarity, itemStyle: {
                color: rarityColors[item.rarity]
            }
        }));
        if (dom) {
            const myChart = echarts.init(dom, null, {
                renderer: 'canvas', useDirtyRect: false
            });
            const app = {};
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
        }
    })
}




