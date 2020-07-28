package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setEnableStatus(1);
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            //2.product進db
            try {
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("創建商品失敗");
                }
            } catch (Exception e) {
                throw new ProductOperationException("創建商品失敗" + e.getMessage());
            }
            if (imageHolderList != null && imageHolderList.size() > 0) {
                addProductImgList(product, imageHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    public Product getProductById(Long productId) {
        return productDao.queryProductById(productId);
    }

    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImageHolderList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            //縮圖-
            if (thumbnail != null) {
                Product tempProduct = productDao.queryProductById(product.getProductId());
                //如果原本有、就先刪除再添加新的
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            //詳情圖片
            if (productImageHolderList != null && productImageHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImageHolderList);
            }
            //2.product進db
            try {
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("更新商品失敗");
                }
            } catch (Exception e) {
                throw new ProductOperationException("更新商品失敗" + e.getMessage());
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageUtil.pageIndexToRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.quertProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setCount(count);
        pe.setProductList(productList);
        return pe;
    }

    /**
     * 1.刪除數據庫中的地址
     * 2.刪除硬碟中的數據
     * //TODO 應該先改完數據庫才能刪圖片，因為如果出錯回滾、硬碟數據是回不來的
     *
     * @param productId:
     * @Author: l5125
     * @Date: 2020/6/23
     * @return: void
     */
    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg p : productImgList) {
            ImageUtil.deleteFileOrPath(p.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }

    //處裡詳情圖
    private void addProductImgList(Product product, List<ImageHolder> productImageHolderList) {
        //List<ProductImg>
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<String> imgList = ImageUtil.generateThumbnails(productImageHolderList, dest);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        for (String imgAddr : imgList) {
            ProductImg productImg = new ProductImg();
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            productImg.setImgAddr(imgAddr);
            productImgList.add(productImg);
        }
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("創建商品詳情圖失敗");
                }
            } catch (Exception e) {
                throw new ProductOperationException("創建商品詳情圖異常" + e.toString());
            }
        }
    }

    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

}
