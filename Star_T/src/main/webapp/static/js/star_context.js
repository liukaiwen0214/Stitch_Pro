// 获取当前页面的上下文路径
const contextPath = window.location.pathname.split('/')[1];
// 拼接完整的请求URL
const requestUrl = '/' + contextPath;


// 使用 fetch API 获取列表数据
fetch(requestUrl + '/countCharactersByRarity')
    .then(response => response.json())
    .then(data => {
        console.log('获取到的列表数据:', data);
        const echartsData = data.map(item => ({
            value: item.count,
            name: item.rarity
        }));

        const dom = document.getElementById('container');
        const myChart = echarts.init(dom, null, {
            renderer: 'canvas',
            useDirtyRect: false
        });
        const app = {};

        let option;

        option = {
            title: {
                text: '式神数量统计',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                top: '5%',
                left: 'center'
            },
            series: [
                {
                    name: '式神统计',
                    type: 'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    itemStyle: {
                        borderRadius: 10,
                        borderColor: '#fff',
                        borderWidth: 2
                    },
                    label: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: 40,
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data:echartsData
                }
            ]
        };

        if (option && typeof option === 'object') {
            myChart.setOption(option);
        }

        window.addEventListener('resize', myChart.resize);


    })
    .catch(error => {
        console.error('获取列表数据时出错:', error);
    });


