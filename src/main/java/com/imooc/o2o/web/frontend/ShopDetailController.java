package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 獲取店鋪訊息及該店鋪下面的商品類別列表
     *
     * @param request:
     * @Author: l5125
     * @Date: 2020/6/28
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     **/
    @RequestMapping(value = "/listshopdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            shop = shopService.queryByShopId(shopId);
            productCategoryList = productCategoryService.getProductCategoryByShopId(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 依據分頁列出該店鋪下面所有商品
     *
     * @param request:
     * @Author: l5125
     * @Date: 2020/6/28
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     **/
    @RequestMapping(value = "/listproductsbyshop",method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if ((shopId > -1) && (pageIndex > -1) && (pageSize > -1)) {
            String productName = HttpServletRequestUtil.getString(request, "productName");
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            Product productCondition = compactProductCondition4Search(shopId, productName, productCategoryId);
            ProductExecution productList = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", productList.getProductList());
            modelMap.put("count", productList.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId or pageSize or pageIndex");
        }
        return modelMap;
    }

    /**
     * 組合查詢條件，並封裝到productCondition對象
     *
     * @param shopId:
     * @param productName:
     * @param productCategoryId:
     * @Author: l5125
     * @Date: 2020/6/28
     * @return: com.imooc.o2o.entity.Product
     **/
    private Product compactProductCondition4Search(long shopId, String productName, long productCategoryId) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        ProductCategory productCategory = new ProductCategory();
        if (shopId != -1L) {
            shop.setShopId(shopId);
            productCondition.setShop(shop);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        if (productCategoryId != -1L) {
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //只允許選出狀態為上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}
