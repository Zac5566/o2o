package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                //給店鋪賦予初始值
                shop.setEnableStatus(0);
                shop.setCreateTime(new Date());
                shop.setLastEditTime(new Date());
                //添加店鋪訊息
                int effectedNum = shopDao.insertShop(shop);
                if (effectedNum < 0) {
                    throw new ShopOperationException("店鋪創建失敗");
                } else {
                    if (thumbnail.getImage() != null) {
                        //存儲圖片
                        try {
                            addShopImg(shop, thumbnail);
                        } catch (Exception e) {
                            throw new ShopOperationException("addShopImg error:" + e.getMessage());
                        }
                        //更新店鋪圖片
                        effectedNum = shopDao.updateShop(shop);
                        if (effectedNum < 0) {
                            throw new ShopOperationException("更新店鋪圖片失敗");
                        }
                    }
                }
            } catch (Exception e) {
                throw new ShopOperationException("添加店鋪錯誤" + e.getMessage());
            }
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    public ShopExecution updateShop(Shop shop,ImageHolder thumbnail) {
        //1.是否有更改圖片
        try {
            if (shop == null || shop.getShopId() == null) {
                return new ShopExecution(ShopStateEnum.NULL_SHOP);
            } else {
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
            }
            //2.更改商店內容
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.updateShop(shop);
            if (effectedNum < 0) {
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            } else {
                shop = shopDao.queryByShopId(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS, shop);
            }
        } catch (Exception e) {
            throw new ShopOperationException("modifyShopError" + e.getMessage());
        }
    }

    public Shop queryByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {

        int rowIndex = PageUtil.pageIndexToRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if (shopList != null) {
            shopExecution.setCount(count);
            shopExecution.setShopList(shopList);
        }else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

    private void addShopImg(Shop shop, ImageHolder imageHolder) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(imageHolder, dest);
        shop.setShopImg(shopImgAddr);
    }
}
