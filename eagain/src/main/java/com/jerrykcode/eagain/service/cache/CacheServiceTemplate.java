package com.jerrykcode.eagain.service.cache;

import org.springframework.stereotype.Service;

@Service
public abstract class CacheServiceTemplate {

    protected abstract Object getFromCache(Object key);
    protected abstract Object getFromMySql(Object key);
    protected abstract void storeInCache(Object key, Object value);
    protected abstract void storeInCacheWithEmptyValue(Object key);

    protected Object get(Object key) {
        Object value = getFromCache(key); //从缓存中查找
        if (value != null) {
            return value;
        }
        //以下为缓存中不存在的情况
        synchronized (key) { //加锁防止缓存击穿
            if ((value = getFromCache(key)) != null)
                return value;
            value = getFromMySql(key); //从mysql中查找
            if (value != null) {
                //若mysql中存在
                storeInCache(key, value); //写入缓存
                return value; //返回
            }
            //以下为mysql中也不存在的情况
            storeInCacheWithEmptyValue(key); //缓存中让key对应一个空值(非null) 防止缓存穿透
        }
        return null;
    }
}
