package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    @Ignore
    public void testAInsertProduct(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        Product product1 = new Product();
        product1.setProductName("測試1");
        product1.setProductDesc("tttttt");
        product1.setImgAddr("圖片地址1");
        product1.setNormalPrice("100");
        product1.setPromotionPrice("80");
        product1.setPriority(2);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setEnableStatus(0);
        product1.setProductCategory(productCategory);
        product1.setShop(shop);
        Product product2 = new Product();
        product2.setProductName("測試2");
        product2.setProductDesc("tttttt");
        product2.setImgAddr("圖片地址2");
        product2.setNormalPrice("200");
        product2.setPromotionPrice("80");
        product2.setPriority(3);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setEnableStatus(0);
        product2.setProductCategory(productCategory);
        product2.setShop(shop);
        int effectedNum1 = productDao.insertProduct(product1);
        int effectedNum2 = productDao.insertProduct(product2);
        assertEquals(1,effectedNum1);
        assertEquals(1,effectedNum2);
    }
    @Test
    @Ignore
    public void TestBQueryProductById(){
        Product product = productDao.queryProductById(50L);
        assertEquals(2,product.getProductImgList().size());
    }
    @Test
    @Ignore
    public void TestCUpdateProduct(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);
        Product product1 = new Product();
        product1.setShop(shop);
        product1.setProductName("測試2");
        product1.setProductDesc("tttttt");
        product1.setImgAddr("圖片地址2");
        product1.setNormalPrice("200");
        product1.setPromotionPrice("280");
        product1.setPriority(3);
        product1.setLastEditTime(new Date());
        product1.setEnableStatus(1);
        product1.setProductId(55L);
        product1.setProductCategory(productCategory);
        int effectedNum = productDao.updateProduct(product1);
        assertEquals(1,effectedNum);
    }
    @Test
    @Ignore
    public void testDQueryProductList(){
        //查詢商品列表並分類，可以輸入的條件有: 商品名(模糊)、商品狀態、店鋪ID、商品類別
        Product productCondition = new Product();
        //店鋪ID
        Shop shop = new Shop();
        shop.setShopId(29L);
        //商品類別
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(3L);
        productCondition.setProductCategory(productCategory);
        //商品狀態
        productCondition.setEnableStatus(1);
        //商品名(模糊)
        productCondition.setProductName("黄");
        List<Product> productList = productDao.queryProductList(productCondition, 0, 100);
        assertEquals(1,productList.size());
    }
    @Test
    @Ignore
    public void testEQuertProductCount(){
        //查詢對應的商品總數，可以輸入的條件有: 商品名(模糊)、商品狀態、店鋪ID、商品類別
        Product productCondition = new Product();
        //店鋪ID
        Shop shop = new Shop();
        shop.setShopId(29L);
        //商品類別
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(3L);
        productCondition.setProductCategory(productCategory);
        //商品狀態
        productCondition.setEnableStatus(1);
        //商品名(模糊)
        productCondition.setProductName("黄");
        int effectedNum = productDao.quertProductCount(productCondition);
        assertEquals(1,effectedNum);

    }
    @Test
    public void TestFUpdateProdudctCategoryToNull(){
        int effectedNum = productDao.updateProdudctCategoryToNull(33L);
        assertEquals(1,effectedNum);
    }

}
