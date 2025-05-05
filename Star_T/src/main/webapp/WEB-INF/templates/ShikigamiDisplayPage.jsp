<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>阴阳师式神展示</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/godinformation.css">
</head>

<body class="bg-gray-100">
<!-- 筛选区域 -->
<div class="container mx-auto px-4 py-4 flex flex-wrap gap-2 items-center">
    <button class="border border-gray-300 rounded-md px-3 py-1 hover:bg-gray-200 active" data-filter="all">全部</button>
    <button class="border border-gray-300 rounded-md px-3 py-1 hover:bg-gray-200" data-filter="N">N</button>
    <button class="border border-gray-300 rounded-md px-3 py-1 hover:bg-gray-200" data-filter="R">R</button>
    <button class="border border-gray-300 rounded-md px-3 py-1 hover:bg-gray-200" data-filter="SR">SR</button>
    <button class="border border-gray-300 rounded-md px-3 py-1 hover:bg-gray-200" data-filter="SSR">SSR</button>
    <button class="border border-gray-300 rounded-md px-3 py-1 hover:bg-gray-200" data-filter="SP">SP</button>
    <label for="search-input"></label><input type="text" id="search-input" class="border border-gray-300 rounded-md px-3 py-1 flex-grow" placeholder="输入式神名称进行模糊查询">
</div>

<!-- 主内容区域 -->
<div class="container mx-auto px-4 py-8">
    <h2 class="text-3xl font-bold text-gray-800 mb-6">所有式神</h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <!-- 这里会通过 JavaScript 动态添加式神卡片 -->
    </div>
</div>

</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="module" src="${pageContext.request.contextPath}/static/js/godinformation.js"></script>
</html>