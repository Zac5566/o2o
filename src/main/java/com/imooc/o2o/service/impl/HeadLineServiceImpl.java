package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exceptions.HeadLineOperationException;
import com.imooc.o2o.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("headLineService")
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static final String HLLISTKEY = "headlinelist";


    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        List<HeadLine> headLineList = null;
        ObjectMapper mapper = new ObjectMapper();
        String key = HLLISTKEY;
        //判斷為按照狀態查詢或是查詢全部
        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
            key = HLLISTKEY + "_" + headLineCondition.getEnableStatus();
        }
        if (!jedisKeys.exists(key)) {
            //如果redis中找不到
            //由數據庫中提取
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            try{
                //並放入redis
                String value = mapper.writeValueAsString(headLineList);
                jedisStrings.set(key,value);
            }catch (JsonProcessingException e){
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        } else {
            String value = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class,HeadLine.class);
            try {
                headLineList = mapper.readValue(value,javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }
}
