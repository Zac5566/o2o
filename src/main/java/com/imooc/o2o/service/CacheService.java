package com.imooc.o2o.service;

public interface CacheService {

    /**
     * 根據key的前綴刪除匹配該模式下的所有key-value
     * @Author: l5125
     * @Date: 2020/6/30
     * @param prefix:
     * @return: void
     */
    void removeFromCache(String prefix);
}
