package com.imooc.o2o.interceptor.shopadmin;

import com.imooc.o2o.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//攔截商店管理系統的權限
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //獲取當前選擇的店舖
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //獲取該用戶能操作的店舖
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
        //判斷該店是不是屬於正在訪問的用戶
        for (Shop shop : shopList) {
            if (shop.getShopId() == currentShop.getShopId()) {
                return true;
            }
        }
        return false;
    }
}
