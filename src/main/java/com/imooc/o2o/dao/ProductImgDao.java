package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    int batchInsertProductImg(List<ProductImg> productImgs);

    int deleteProductImgByProductId(Long productId);

    List<ProductImg> queryProductImgList(Long productId);
}
