package com.imooc.o2o.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

import java.util.Set;

public class JedisUtil {
    //操作key的方法
    public Keys KEYS;
    // 對存儲結構為String類型的操作
    public Strings STRINGS;
    //連接池對象
    private JedisPool jedisPool;

    public JedisPool getJedisPool(){
        return jedisPool;
    }
    public void  setJedisPool(JedisPoolWriper jedisPoolWriper){
        this.jedisPool = jedisPoolWriper.getJedisPool();
    }
    /**
     * 從jedis連接池中獲取jedis對象
     * @Author: l5125
     * @Date: 2020/6/29
     * @return: redis.clients.jedis.Jedis
     */
    public Jedis getJedis(){
        return jedisPool.getResource();
    }
    //*********************KeyS*********
    public class Keys{

        /**
         * 清空所有key
         * @Author: l5125
         * @Date: 2020/6/29
         * @return: java.lang.String 返回被刪除的key數據
         */
        public String flushAll(){
            Jedis jedis = getJedis();
            String sdata = jedis.flushAll();
            jedis.close();
            return sdata;
        }
        /**
         * 刪除key對應的紀錄，可以是多條
         * @Author: l5125
         * @Date: 2020/6/29
         * @param keys:
         * @return: long 刪除的紀錄數
         */
        public long del(String... keys){
            Jedis jedis = getJedis();
            long count = jedis.del(keys);
            jedis.close();
            return count;
        }
        /**
         * 查詢key是否存在
         * @Author: l5125
         * @Date: 2020/6/29
         * @param key:
         * @return: boolean
         */
        public boolean exists(String key){
            Jedis jedis = getJedis();
            Boolean exists = jedis.exists(key);
            jedis.close();
            return exists;
        }
        /**
         * 查詢所有給定的模式的鍵
         * @Author: l5125
         * @Date: 2020/6/29
         * @param pattern: key的表達式， *表示多個， ?表示一個
         * @return: java.util.Set<java.lang.String>
         */
        public Set<String> keys(String pattern){
            Jedis jedis = getJedis();
            Set<String> set = jedis.keys(pattern);
            jedis.close();
            return set;
        }
    }
    //***********************Strings***************
    public class Strings{

        /**
         * 根據key獲取紀錄
         * @Author: l5125
         * @Date: 2020/6/29
         * @param key:
         * @return: java.lang.String
         */
        public String get(String key){
            Jedis jedis = getJedis();
            String value = jedis.get(key);
            jedis.close();
            return value;
        }
        /**
         * 添加紀錄，如果存在則覆蓋原本的value
         * @Author: l5125
         * @Date: 2020/6/29
         * @param key:
         * @param value:
         * @return: java.lang.String 狀態碼
         */
        public String set(String key,String value){
            return set(SafeEncoder.encode(key),SafeEncoder.encode(value));
        }
        /**
         * 添加紀錄，如果存在則覆蓋原本的value
         * @Author: l5125
         * @Date: 2020/6/29
         * @param key:
         * @param value:
         * @return: java.lang.String 狀態碼
         */
        public String set(byte[] key,byte[] value){
            Jedis jedis = getJedis();
            String status = jedis.set(key, value);
            jedis.close();
            return status;
        }
    }


}
