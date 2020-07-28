package com.imooc.o2o.dao;

import com.imooc.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {

    /**
     * 傳入頭條的可用狀態來查詢
     * @Author: l5125
     * @Date: 2020/6/25
     * @param headLineCondition:
     * @return: java.util.List<com.imooc.o2o.entity.HeadLine>
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition")HeadLine headLineCondition);
}
