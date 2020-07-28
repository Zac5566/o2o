package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    int insertProduct(Product product);

    int updateProduct(Product product);

    Product queryProductById(Long productId);
    
    /**
     * 查詢商品列表並分類，可以輸入的條件有: 商品名(模糊)、商品狀態、店鋪ID、商品類別
     * @Author: l5125
     * @Date: 2020/6/24
     * @param productCondition:
     * @param rowIndex: 
     * @param pageSize: 
     * @return: java.util.List<com.imooc.o2o.entity.Product>
     */
    List<Product> queryProductList(@Param("productCondition")Product productCondition
            ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
    /**
     * 查詢對應的商品總數，可以輸入的條件有: 商品名(模糊)、商品狀態、店鋪ID、商品類別
     * @Author: l5125
     * @Date: 2020/6/24
     * @param productCondition:
     * @return: int
     */
    int quertProductCount(@Param("productCondition")Product productCondition);

    /**
     * 把product中的ProdudctCategory設為空
     * @Author: l5125
     * @Date: 2020/6/24
     * @param productCategoryId:
     * @return: int
     */
    int updateProdudctCategoryToNull(Long productCategoryId);
}
