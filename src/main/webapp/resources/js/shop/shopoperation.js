//註冊、修改、查詢店鋪
$(function () {
    var shopId = getQueryString("shopId");//獲取url中get請求的id
    var isEdit = shopId ? true : false;
    var initUrl = "/o2o/shopadmin/getshopinitinfo";
    var registerUrl = "/o2o/shopadmin/registershop";
    var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId="+shopId;
    var editShopUrl = "/o2o/shopadmin/modifyshop";
    if(!isEdit){
        getShopInitInfo();
    }else {
        getShopInfo(shopId);
    }
    //查詢店鋪訊息
    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="' + shop.shopCategory.shopCategoryId
                    + '"selected="selected">' + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled','disabled');
                $('#shop-area').html(tempAreaHtml);
                $("#shop-area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");

            }
        });
    }
    //新增店鋪時所需的資訊(店鋪類別+區域列表)
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#shop-area').html(tempAreaHtml);
            }
        });
    }
    //新建或修改商店
    $('#submit').click(function () {
        var shop = {};
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId: $('#shop-area').find('option').not(function(){
                return !this.selected;
            }).data('id')
        };
        if(isEdit) shop.shopId = shopId;
        //$('#shop-img')返回的是一个列表，由于只有一个id为shop-img的元素，
        // 所以$('#shop-img')[0]就是获取具体元素，而$('#shop-img')[0]中
        // 的文件也是一个列表，由于只有一个文件，所以$('#shop-img')[0].files[0]就是获取就具体的文件数据
        var shopImg = $('#shop-img')[0].files[0];
        var formData = new FormData();
        var verifyCodeActual = $('#kaptcha').val();
        if(verifyCodeActual==null){
            $.toast('請輸入驗證碼!');
            return;
        }
        formData.append("verifyCodeActual",verifyCodeActual)
        formData.append("shopImg",shopImg);
        formData.append("shopStr",JSON.stringify(shop));
        $.ajax({
            url : (isEdit? editShopUrl:registerUrl),
            type :'post',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success: function (data) {
                if(data.success){
                    $.toast("提交成功");
                }else {
                    $.toast("提交失敗"+data.errMsg);
                }
            }
        });
    });
})