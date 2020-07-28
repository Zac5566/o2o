package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller("shopListController")
@RequestMapping(value = "/frontend")
public class ShopListController {

    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopService shopService;

    /**
     * 返回商品列表裡的ShopCategory列表(二級或一級)，以及區域訊息列表
     *
     * @param request:
     * @Author: l5125
     * @Date: 2020/6/26
     * @return: java.util.Map<java.lang.String, java.lang.Object> shopCategoryList,areaList
     */
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        //查詢shopCategory
        if (parentId > -1L) {
            //傳入shopCategoryId,查詢二級
            try {
                ShopCategory shopCategory = new ShopCategory();
                ShopCategory parentShopCategory = new ShopCategory();
                parentShopCategory.setShopCategoryId(parentId);
                shopCategory.setParent(parentShopCategory);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategory);
                modelMap.put("shopCategoryList", shopCategoryList);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            //查詢一級
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
                modelMap.put("shopCategoryList", shopCategoryList);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        //查詢區域
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        modelMap.put("success", true);
        return modelMap;
    }

    /**
     * 獲取指定條件下的店鋪列表
     *
     * @param request:
     * @Author: l5125
     * @Date: 2020/6/26
     * @return: java.util.Map<java.lang.String, java.lang.Object> shopList
     */
    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop shopCondition = new Shop();
        //封裝查詢對象
        try{
            String name = HttpServletRequestUtil.getString(request, "shopName");
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            shopCondition = compactShopCondition4Search(name, parentId, shopCategoryId, areaId);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        //查詢
        try{
            int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
            int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("count",se.getCount());
            modelMap.put("shopList",se.getShopList());
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        modelMap.put("success",true);
        return modelMap;
    }

    /**
     * 處理get請求中的參數
     * @Author: l5125
     * @Date: 2020/6/27
     * @param shopName:
     * @param parentId:
     * @param shopCategoryId:
     * @param areaId:
     * @return: com.imooc.o2o.entity.Shop
     */
    private  Shop compactShopCondition4Search(String shopName, Long parentId, Long shopCategoryId, int areaId) {
        Shop shopCondition = new Shop();
        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        if (parentId > -1L) {
            ShopCategory childShopCategory = new ShopCategory();
            ShopCategory parentShopCategory = new ShopCategory();
            parentShopCategory.setShopCategoryId(parentId);
            childShopCategory.setParent(parentShopCategory);
            shopCondition.setShopCategory(childShopCategory);
        }
        if (shopCategoryId > -1L) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if(areaId > -1){
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        return shopCondition;
    }
}
