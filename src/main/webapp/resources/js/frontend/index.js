$(function () {
    var url = '/o2o/frontend/listmainpageinfo';
    $.getJSON(url,function (data) {
        if (data.success){
            //輪播圈資料
            var headLineHtml = '';
            data.headLineList.map(function (item,index) {
                headLineHtml +=
                    '<div class="swiper-slide img-wrap"><img class="banner-img" src="'
                    + item.lineImg
                    + '"alt=""/></div>'
            });
            $('.swiper-wrapper').html(headLineHtml);
            //輪播圈設定
            //设定轮播图轮换时间为3秒
            $(".swiper-container").swiper({
                autoplay : 3000,
                //用户对轮播图进行操作时，是否自动停止autoplay
                autoplayDisableOnInteraction : false
            });
            //一級列表資料
            var categoryHtml = '';
            data.shopCategoryList.map(function (item,index) {
                categoryHtml +=
                    '<div class="col-50 shop-classify" data-category="'
                    + item.shopCategoryId
                    + '"><div class="word"><p class="shop-title">'
                    + item.shopCategoryName
                    + '</p><p class="shop-desc">'
                    + item.shopCategoryDesc
                    + '</p></div><div class="shop-classify-img-warp">'
                    +'<img class="shop-img" src="'
                    + item.shopCategoryImg
                    + '"></div></div>'
            });
            $('.row').html(categoryHtml);
        }
    });
    $('#me').on('click',function () {
        $.openPanel('#panel-right-demo');
    });
    $('.row').on('click','.shop-classify',function (e) {
        var categoryId = e.currentTarget.dataset.category;
        var newUrl = "/o2o/frontend/shoplist?parentId="+categoryId;
        window.location.href = newUrl;
    });
});