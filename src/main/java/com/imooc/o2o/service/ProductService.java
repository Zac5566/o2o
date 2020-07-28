package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService {

    //添加商品訊息即圖片
    ProductExecution addProduct(Product product, ImageHolder thumbnail
            , List<ImageHolder> imageHolderList)throws ProductOperationException;
    
    /**
     * 通過商品ID查詢唯一商品
     * @Author: l5125
     * @Date: 2020/6/23
     * @param productId:
     * @return: com.imooc.o2o.entity.Product
     */
    Product getProductById(Long productId);
    
    /**
     * 修改商品訊息以及圖片
     * @Author: l5125
     * @Date: 2020/6/23
     * @param product:
     * @param thumbnail:
     * @param imageHolderList:
     * @return: com.imooc.o2o.dto.ProductExecution
     */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail
            ,List<ImageHolder> imageHolderList) throws ProductOperationException;

    /**
     * 查詢商品列表並分類，可以輸入的條件有: 商品名(模糊)、商品狀態、店鋪ID、商品類別
     * @Author: l5125
     * @Date: 2020/6/24
     * @param productCondition:
     * @param pageIndex:
     * @param pageSize:
     * @return: com.imooc.o2o.dto.ProductExecution
     */
    ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
}
