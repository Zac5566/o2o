package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void testAinsertShop(){
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        Area area = new Area();
        area.setAreaId(2);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(10L);
        Shop shop = new Shop();
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("測試的店舖");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("審核中");
        int affectNum = shopDao.insertShop(shop);
        assertEquals(1,affectNum);
    }
    @Test
    @Ignore
    public void testBupdateShop(){
        Shop shop = new Shop();
        shop.setShopId(40L);
        shop.setShopName("pchome");
        shop.setShopDesc("testq");
        shop.setShopAddr("台北");
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("通過");
        int affectNum = shopDao.updateShop(shop);
        assertEquals(1,affectNum);
    }
    @Test
    @Ignore
    public void testCQueryByShopId(){
        Shop shop = shopDao.queryByShopId(1L);
        System.out.println(shop.getShopName());
        System.out.println(shop.getShopAddr());
    }
    @Test
    @Ignore
    public void testDQueryShopListAndCount(){
        //shopList test
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        System.out.println(shopList.size());
        int count = shopDao.queryCount(shopCondition);
        System.out.println("總店鋪數"+count);

        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(10L);
        shopCondition.setShopCategory(sc);
        int count1 = shopDao.queryCount(shopCondition);
        System.out.println(count1);
    }
    @Test
    @Ignore
    public void testEQueryShopListAndCount(){
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(12L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        shopCondition.setShopName("黄人");

        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 999);
        System.out.println(shopList.size());
    }
    @Test
    public void testFFuzzyQueryByName(){
        List<Shop> shopList = shopDao.fuzzyQueryByName("測試");
        System.out.println(shopList.size());
    }
}

