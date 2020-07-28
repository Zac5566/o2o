package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.Cache;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;

    @Test
    public void testAreaList(){
        List<Area> areaList = areaService.getAreaList();
        Assert.assertEquals("西苑", ((Area)areaList.get(0)).getAreaName());
//        AreaService var10001 = areaService;
        cacheService.removeFromCache("arealist");
        areaList = this.areaService.getAreaList();

    }
}
