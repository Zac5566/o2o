package com.imooc.o2o.service;

import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.service.impl.HeadLineServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
    public Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}
