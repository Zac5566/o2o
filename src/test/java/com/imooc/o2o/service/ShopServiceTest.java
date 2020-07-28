package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

import static org.junit.Assert.assertEquals;


public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
//    @Ignore
    public void testAddShop() throws FileNotFoundException {
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
        shop.setShopName("測試的店舖3");
        shop.setShopDesc("test32");
        shop.setShopAddr("test32");
        shop.setPhone("test32");
        shop.setShopImg("test32");
        shop.setPriority(1);
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("審核中");
        File shopImg = new File("C:\\Users\\L\\Desktop\\test.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder thumbnail = new ImageHolder(is,shopImg.getName());
        ShopExecution shopExecution = shopService.addShop(shop,thumbnail);
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());

    }
    @Test
    @Ignore
    public void testUpdateShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("修改後的店舖名稱");
        File file = new File("C:\\Users\\L\\Desktop\\img\\baobao.png");
        InputStream is = new FileInputStream(file);
        ImageHolder thumbnail = new ImageHolder(is,file.getName());
        ShopExecution shopExecution = shopService.updateShop(shop, thumbnail);
        System.out.println("新的圖片地址"+shopExecution.getShop().getShopImg());
    }
    @Test
    @Ignore
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        area.setAreaId(2);
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        shopCondition.setArea(area);
        ShopExecution shopList = shopService.getShopList(shopCondition, 4, 3);
        System.out.println("總數為"+shopList.getCount());
        System.out.println("第4頁有"+shopList.getShopList().size());
    }
}
