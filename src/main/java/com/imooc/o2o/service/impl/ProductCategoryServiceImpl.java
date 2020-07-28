package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    public List<ProductCategory> getProductCategoryByShopId(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> list)
            throws ProductCategoryOperationException {
        if (list != null && list.size() > 0) {
            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(list);
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("商品類別添加失敗");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Transactional
    public ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId)
            throws ProductCategoryOperationException {
        //先將product中的productCategoryId置為空
        try {
            int effectedNum = productDao.updateProdudctCategoryToNull(productCategoryId);
            if (effectedNum < 0) {
                throw new ProductCategoryOperationException("商品類別更新失敗");
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("商品類別更新失敗"+e.getMessage());
        }
        //刪除分類
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum > 0) {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            } else {
                throw new ProductCategoryOperationException("商品類別刪除失敗");
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("刪除商品分類錯誤:" + e.getMessage());
        }
    }
}
