package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exceptions.ShopCategoryOperationException;
import com.imooc.o2o.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("shopCategory")
public class ShopCategoryImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;


    Logger logger = LoggerFactory.getLogger(ShopCategoryImpl.class);

    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        List<ShopCategory> shopCategoryList = null;
        ObjectMapper mapper = new ObjectMapper();
        String key = SCLISTKEY;
        if (shopCategoryCondition == null) {
            //選父類
            key = key + "_allfirstlevel";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null
                && shopCategoryCondition.getParent().getShopCategoryId() != null) {
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
            //選某父類底下的子類
        } else if (shopCategoryCondition != null) {
            //選全部的子類
            key = key + "_allsecondlevel";
        }
        if(!jedisKeys.exists(key)){
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            try {
                String value = mapper.writeValueAsString(shopCategoryList);
                jedisStrings.set(key,value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
        }else {
            String value = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class,ShopCategory.class);
            try {
                shopCategoryList = mapper.readValue(value,javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}
