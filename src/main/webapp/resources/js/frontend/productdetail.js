//獲取商品詳情
$(function () {
    //從地址取id
    var productId = getQueryString('productId');
    //詳情url
    var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='+productId;

    $.getJSON(productUrl,function (data) {
        if(data.success){
            var product = data.product;
            //商品名稱
            $('#product-name').text(product.productName);
            //商品縮圖
            $('#product-img').attr("src",product.imgAddr);
            //更新時間
            $('#product-time').text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
            //商品描述
            $('#product-desc').text(product.productDesc);
            //商品點數
            $('#product-point').text(product.point);
            //商品價格(normalPrice,promotionPrice排列組合，除了皆空)
            if(product.normalPrice != undefined && product.promotionPrice != undefined){
                $('#price').show();
                $('#normalPrice').html(
                    '<del>'+'￥'+product.normalPrice+'</del>');
                $('#promotionPrice').text('￥'+product.promotionPrice);
            }else if(product.promotionPrice == undefined){
                $('#normalPrice').text('￥'+product.normalPrice);
            }else if(product.normalPrice == undefined && product.promotionPrice != undefined){
                $('#promotionPrice').text('￥'+product.promotionPrice);
            };
            var imgListHtml = '';
            //詳情圖片
            product.productImgList.map(function (item,index) {
                imgListHtml +=
                    '<div><img src="'
                    + item.imgAddr
                    +'" width="100%"/></div>';
            });
            $('#imgList').html(imgListHtml);
        }
    });
    // 点击后打开右侧栏
    $('#me').click(function() {
        $.openPanel('#panel-right-demo');
    });
    $.init();

});