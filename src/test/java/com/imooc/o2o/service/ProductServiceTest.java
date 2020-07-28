package com.imooc.o2o.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;
    @Test
    @Ignore
    public void testAddProduct() throws FileNotFoundException {
        //創建shop為1且ProductCategoryId為1的商品
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("測試1");
        product.setProductDesc("描述1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //創建縮圖
        File file = new File("C:\\Users\\L\\Desktop\\test.jpg");
        InputStream is = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder(is,file.getName());
        //創建詳細圖片
        File file2 = new File("C:\\Users\\L\\Desktop\\test.jpg");
        InputStream is2 = new FileInputStream(file);
        ImageHolder imageHolder2 = new ImageHolder(is2,file2.getName());
        File file3 = new File("C:\\Users\\L\\Desktop\\test.jpg");
        InputStream is3 = new FileInputStream(file);
        ImageHolder imageHolder3 = new ImageHolder(is3,file3.getName());
        List<ImageHolder> imageHolderList = new ArrayList<ImageHolder>();
        imageHolderList.add(imageHolder2);
        imageHolderList.add(imageHolder3);
        ProductExecution productExecution = productService.addProduct(product, imageHolder, imageHolderList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());
    }
    @Test
    public void testBModifyProduct() throws FileNotFoundException {
        //創建shop為1且ProductCategoryId為1的商品
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setProductId(55L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("測試ModifyProduct");
        product.setProductDesc("描述ModifyProduct");
        product.setPriority(30);
        product.setLastEditTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //創建縮圖
        File file = new File("C:\\Users\\L\\Desktop\\74677142_1426683017487285_2279239114198876160_n.png");
        InputStream is = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder(is,file.getName());
        //創建詳細圖片
        File file2 = new File("C:\\Users\\L\\Desktop\\test.jpg");
        InputStream is2 = new FileInputStream(file2);
        ImageHolder imageHolder2 = new ImageHolder(is2,file2.getName());
        File file3 = new File("C:\\Users\\L\\Desktop\\baobao.png");
        InputStream is3 = new FileInputStream(file3);
        ImageHolder imageHolder3 = new ImageHolder(is3,file3.getName());
        List<ImageHolder> imageHolderList = new ArrayList<ImageHolder>();
        imageHolderList.add(imageHolder2);
        imageHolderList.add(imageHolder3);
        ProductExecution productExecution = productService.modifyProduct(product, imageHolder, imageHolderList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());
    }


}
