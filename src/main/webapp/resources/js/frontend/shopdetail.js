$(function () {
    var loading = false;
    //得到shopID
    var shopId = getQueryString('shopId');
    //商店詳情網址
    var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
    //商品詳情與查詢
    var listUrl = '/o2o/frontend/listproductsbyshop';
    //商品最大加載數
    var maxItems = 20;
    //每頁顯示數
    var pageSize = 3;
    //頁碼
    var pageNum = 1;
    //商品名稱
    var productName = '';

    var productCategoryId = '';

    //初始化頁面
    getSearchDivData();
    addItems(pageSize, pageNum);

    /**
     * 加載商店詳情
     * @Author: l5125
     * @Date: 2020/6/28
     * @param null:
     * @return: null
     **/
    function getSearchDivData() {
        $.getJSON(searchDivUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                //商店名
                $('#shop-name').text(shop.shopName);
                //商店縮圖
                $('#shop-cover-pic').attr('src', shop.shopImg);
                //商店最後更新時間
                $('#shop-update-time').html(
                    new Date(shop.lastEditTime).Format("yyyy-MM-dd")
                );
                //商店描述
                $('#shop-desc').text(shop.shopDesc);
                //商店地址
                $('#shop-addr').text(shop.shopAddr);
                //商店電話
                $('#shop-phone').text(shop.phone);
                //該商店的商品類別
                var html = '';
                data.productCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-product-search-id="'
                        + item.productCategoryId
                        + '">'
                        + item.productCategoryName
                        + '</a>';
                });
                $('#shopdetail-button-div').html(html);
            }
        });
    }

    /**
     * 分頁展示商品
     * @Author: l5125
     * @Date: 2020/6/28
     * @param null:
     * @return: null
     **/
    function addItems(pageSize, pageIndex) {
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&productCategoryId=' + productCategoryId
            + '&productName=' + productName + '&shopId=' + shopId;
        //開始加載
        loading = true;
        $.getJSON(url, function (data) {
            var html = '';
            var product = data.productList;
            if (data.success) {
                maxItems = data.count;
                product.map(function (item, index) {
                    html += '<div class="card" data-product-id="'
                        + item.productId
                        + '"><div class="card-header">'
                        + item.productName
                        + '</div><div class="card-content">'
                        + '<div class="list-block media-list">'
                        + '<ul><li class="item-content"><div class="item-media"><img src="'
                        + item.imgAddr
                        + '"width="44"></div><div class="item-inner">'
                        + '<div class="item-subtitle"></div></div></li></ul></div></div>'
                        + '<div class="card-footer"><span>'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
            }
            //顯示已經加載的商品數
            var total = $('.list-div .card').length;
            if (total >= maxItems) {
                $('.infinite-scroll-preloader').hide();
            } else {
                $('.infinite-scroll-preloader').show();
            }
            //頁碼+1
            pageNum += 1;
            //加載結束
            loading = false;
            $.refreshScroller();
        });
    }

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });
    // 选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
    $('#shopdetail-button-div').on(
        'click',
        '.button',
        function (e) {
            // 获取商品类别Id
            productCategoryId = e.target.dataset.productSearchId;
            if (productCategoryId) {
                // 若之前已选定了别的category,则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    productCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });
    // 点击商品的卡片进入该商品的详情页
    $('.list-div').on(
        'click',
        '.card',
        function (e) {
            var productId = e.currentTarget.dataset.productId;
            window.location.href = '/o2o/frontend/productdetail?productId='
                + productId;
        });
    // 需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
    $('#search').on('change', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });
    // 点击后打开右侧栏
    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });
    $.init();
});