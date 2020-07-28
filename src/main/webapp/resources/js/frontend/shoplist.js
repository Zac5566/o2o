$(function () {
    // 加载flag
    var loading = false;
    // 最多可加载的条目
    var maxItems = 999;
    // 一頁最大的顯示數量
    var pageSize = 3;
    // 店鋪url
    var listUrl = '/o2o/frontend/listshops';
    //區域、分類初始化url
    var searchDivUrl = '/o2o/frontend/listshopspageinfo';
    //頁碼
    var pageNum = 1;
    // 取出get中的參數
    var parentId = getQueryString('parentId');
    var shopName = '';
    var shopCategoryId = '';
    var areaId = '';
    //加載店鋪列表跟區域
    getSearchDivData();
    //加載店鋪
    addItems(pageSize, pageNum);


    /**
     * 获取店铺类别列表以及区域列表信息
     * @Author: l5125
     * @Date: 2020/6/27
     * @return: null
     */
    function getSearchDivData() {
        var url = searchDivUrl + '?' + 'parentId=' + parentId;
        $.getJSON(url, function (data) {
            if (data.success) {
                var shopCategoryList = data.shopCategoryList;
                var html = '';
                html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
                //商店分類
                shopCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-category-id='
                        + item.shopCategoryId
                        + '>'
                        + item.shopCategoryName
                        + '</a>';
                });
                $('#shoplist-search-div').html(html);
                //區域下拉
                var selectOptions = '<option value="">全部街道</option>';
                data.areaList.map(function (item, index) {
                    selectOptions += '<option value="'
                        + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#area-search').html(selectOptions);
            }
        });
    }

    /**
     * 获取分页展示的店铺列表信息
     * @Author: l5125
     * @Date: 2020/6/27
     * @return: null
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        var url = listUrl + '?shopName=' + shopName + '&parentId=' + parentId
            + '&shopCategoryId=' + shopCategoryId + '&areaId=' + areaId + '&pageSize='
            + pageSize + '&pageIndex=' + pageIndex;
        loading = true;
        $.getJSON(url, function (data) {
            //设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
            if (data.success) {
                maxItems = data.count;
                var html = '';
                data.shopList.map(function (item, index) {
                    html += '' +
                        '<div class="card" data-shop-id="'
                        + item.shopId
                        + '"><div class="card-header">'
                        + item.shopName
                        + '</div><div class="card-content"><div class="list-block media-list">'
                        + '<ul><li class="item-content"><div class="item-media"><img src="'
                        + item.shopImg
                        + '" width="44"></div><div class="item-inner"><div class="item-subtitle"">'
                        + item.shopDesc
                        + '</div></div></li></ul></div></div><div class="card-footer"><span>'
                        + new Date(item.lastEditTime).Format('yyyy-MM-dd')
                        + '更新</span><span>'
                        + '點擊查看</span></div></div>'
                });
                $('.list-div').append(html);
                //獲取已加載的card數量
                var total = $('.list-div .card').length;
                //達到數量就不再加載
                if (total >= maxItems) {
                    // 隐藏提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                pageNum += 1;
                // 加載結束
                loading = false;
                //刷新滾動條
                $.refreshScroller();
            }
        });
    }

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite','.infinite-scroll-bottom', function() {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });
    //點擊店鋪進入詳情
    $('.shop-list').on('click', '.card', function(e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
    });
    // 选择新的店铺类别之后，重置页码，清空原先的店铺列表，按照新的类别去查询
    $('#shoplist-search-div').on(
        'click',
        '.button',
        function (e) {
            if (parentId) {// 如果传递过来的是一个父类下的子类
                shopCategoryId = e.target.dataset.categoryId;
                // 若之前已选定了别的category,则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    shopCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                // 由于查询条件改变，清空店铺列表再进行查询
                $('.list-div').empty();
                // 重置页码
                pageNum = 1;
                addItems(pageSize, pageNum);
            } else {// 如果传递过来的父类为空，则按照父类查询
                parentId = e.target.dataset.categoryId;
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    parentId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                // 由于查询条件改变，清空店铺列表再进行查询
                $('.list-div').empty();
                // 重置页码
                pageNum = 1;
                addItems(pageSize, pageNum);
                parentId = '';
            }
        });
    // 区域信息发生变化后，重置页码，清空原先的店铺列表，按照新的区域去查询
    $('#area-search').on('change', function() {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });
    // 初始化页面
    $.init();
});