package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    @Ignore
    public void testABatchInsertProductImg(){
        ProductImg productImg1 = new ProductImg();
        productImg1.setCreateTime(new Date());
        productImg1.setImgAddr("圖片地址1");
        productImg1.setImgDesc("描述");
        productImg1.setPriority(2);
        productImg1.setProductId(53L);
        ProductImg productimg2 = new ProductImg();
        productimg2.setCreateTime(new Date());
        productimg2.setImgAddr("圖片地址2");
        productimg2.setImgDesc("描述");
        productimg2.setPriority(3);
        productimg2.setProductId(53L);
        ProductImg productimg3 = new ProductImg();
        productimg3.setCreateTime(new Date());
        productimg3.setImgAddr("圖片地址3");
        productimg3.setImgDesc("描述");
        productimg3.setPriority(4);
        productimg3.setProductId(53L);
        List<ProductImg> list = new ArrayList<ProductImg>();
        list.add(productImg1);
        list.add(productimg2);
        list.add(productimg3);
        int effectedNum = productImgDao.batchInsertProductImg(list);
        assertEquals(3,effectedNum);

    }
    @Test
    public void testBQueryProductImgList(){
        List<ProductImg> productImgList = productImgDao.queryProductImgList(50L);
        assertEquals(2,productImgList.size());
    }
    @Test
    @Ignore
    public void testCDeleteProductImgByProductId(){
        int efffectedNum = productImgDao.deleteProductImgByProductId(53L);
        assertEquals(3,efffectedNum);
    }

}
