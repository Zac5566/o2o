$(function () {
    // 从URL里获取productId参数的值
    var productId = getQueryString('productId');
    // 通过productId获取商品信息的URL
    var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
    // 获取当前店铺设定的商品类别列表的URL
    var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
    // 更新商品信息的URL
    var productPostUrl = '/o2o/shopadmin/modifyproduct';
    //判斷是否為編輯頁面
    var isEdit = false;
    if(productId){
        //編輯操作
        getInfo(productId);
        isEdit = true;
    }else {
        getProductCategory();
        productPostUrl = '/o2o/shopadmin/addproduct';
    }

    // 获取需要编辑的商品的商品信息，并赋值给表单
    function getInfo(id) {
        $.getJSON(
            infoUrl,
            function (data) {
                if(data.success){
                    var product = data.product;
                    $('#product-name').val(product.productName);
                    $('#priority').val(product.priority);
                    $('#normal-price').val(product.normalPrice);
                    $('#promotion-price').val(product.promotionPrice);
                    $('#product-desc').val(product.productDesc);
                    $('#product-point').val(product.point);
                    //目錄下拉表單
                    var optionHtml = '';
                    var optionArr = data.productCategoryList;
                    var selectedOption = optionArr.productCategoryId;
                    optionArr.map(function (item,index) {
                        var isSelected = selectedOption=== item.productCategoryId ? 'selected':'';
                        optionHtml +=
                            ' <option data-value="'
                            + item.productCategoryId
                            + '"'
                            + isSelected
                            + '>'
                            + item.productCategoryName
                            + '</option>';
                    })
                    $('#product-category').html(optionHtml);
                }
            }
        );
    }
    // 为商品添加操作提供该店铺下的所有商品类别列表
    function getProductCategory() {
        $.getJSON(
            categoryUrl,
            function (data) {
                if(data.success){
                    var optionHtml = '';
                    var categoryList = data.data;
                    categoryList.map(function (item,index) {
                        optionHtml +=
                            '<option data-value="'
                            + item.productCategoryId
                            + '"'
                            + '>'
                            + item.productCategoryName
                            + '</option>';
                    })
                    $('#product-category').html(optionHtml);
                }
            });
    }
    // 针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片），
    // 且控件总数未达到6个，则生成新的一个文件上传控件
    $('.detail-img-div').on('change','.detail-img:last-child',function () {
        if($('.detail-img').length<6){
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    })
    //提交商品(編輯或新增)
    $('#submit').click(function () {
        var product = {};
        product.productName = $('#product-name').val();
        product.priority = $('#priority').val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        product.productDesc = $('#product-desc').val();
        product.point = $('#product-point').val();
        product.productCategory = {'productCategoryId':$('#product-category').find('option').not(function () {
            return !this.selected;
        }).data('value')};
        //如果是編輯就會有productId
        if(isEdit){
            product.productId = productId;
        }
        //縮圖
        var thumbnail = $('.small-img')[0].files[0];
        var formData = new FormData();
        formData.append('thumbnail',thumbnail);
        //詳情圖
        var productImg = $('.detail-img');
        productImg.map(
            function (index ,item) {
                if(productImg[index].files.length > 0){
                    formData.append('productImg'+index,productImg[index].files[0]);
            }
        })

        formData.append('productStr',JSON.stringify(product));
        // formData.append("product",product);
        var verifyCodeActual = $('#kaptcha').val();
        if(!verifyCodeActual){
            $.toast('請輸入驗證碼');
            return;
        }
        formData.append("verifyCodeActual",verifyCodeActual);
        //提交數據
        $.ajax({
            url : productPostUrl,
            type : 'POST',
            data : formData,
            contentType : false,
            processData: false,
            cache : false,
            success : function (data) {
                if(data.success){
                    $.toast('添加成功');
                    $('#kaptcha').click();
                }else {
                    $.toast('添加失敗');
                    $('#kaptcha').click();
                }
            }
        })
    })
})