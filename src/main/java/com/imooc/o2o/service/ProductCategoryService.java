package com.imooc.o2o.service;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getProductCategoryByShopId(Long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> list)
        throws ProductCategoryOperationException;
    ProductCategoryExecution deleteProductCategory(Long productCategoryId,Long shopId)
        throws ProductCategoryOperationException;
}
