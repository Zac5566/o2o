package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testBQueryShopCategory() {
        Long shopId = 28L;
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println(productCategories.size());
    }

    @Test
    public void testABatchInsertProductCategory() {

        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setShopId(39L);
        productCategory1.setProductCategoryName("測試1");
        productCategory1.setCreateTime(new Date());
        productCategory1.setPriority(20);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setShopId(39L);
        productCategory2.setProductCategoryName("測試2");
        productCategory2.setCreateTime(new Date());
        productCategory2.setPriority(20);

        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);

        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testCDeleteProductCategory() {
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(39L);
        for (ProductCategory p : productCategoryList) {
            if (p.getProductCategoryName().equals("測試1") || p.getProductCategoryName().equals("測試2")) {
               int effectedNum = productCategoryDao.deleteProductCategory(p.getProductCategoryId(), p.getShopId());
                assertEquals(1, effectedNum);
            }
        }
    }
}
