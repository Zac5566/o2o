package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    int insertShop(Shop shop);
    int updateShop(Shop shop);
    Shop queryByShopId(Long shopId);
    /*
    * 分頁查詢店鋪
    * 查詢條件
    *   -店鋪名(模糊)、店鋪狀態、店鋪類別、區域id、owner
    * */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition
            ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
    /*
    * 查詢總數
    * */
    int queryCount(@Param("shopCondition")Shop shopCondition);

    List<Shop> fuzzyQueryByName(String shopName);
}
