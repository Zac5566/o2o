package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("areaDao")
public interface AreaDao {
    List<Area> queryAll();
}
