package com.imooc.o2o.service.impl;

import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jediskey;

    @Override
    public void removeFromCache(String prefix) {
        Set<String> keys = jediskey.keys(prefix + "*");
        for(String s:keys){
            jediskey.del(s);
        }
    }
}
