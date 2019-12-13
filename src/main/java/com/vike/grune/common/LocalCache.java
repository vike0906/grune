package com.vike.grune.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author: lsl
 * @createDate: 2019/10/25
 */
public class LocalCache {

    /**构建Token缓存容器,30分钟后过期*/
    private static LoadingCache<String, Long> TOKEN_CACHE = CacheBuilder.newBuilder()
            .initialCapacity(100)
            .concurrencyLevel(10)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Long>() {
                @Override
                public Long load(String s){
                    return null;
                }
            });

    /**储存Token*/
    public static void putToken(String key, Long value){
        TOKEN_CACHE.put(key,value);
    }

    /**获取Token*/
    public static Long getToken(String key){
        return TOKEN_CACHE.getIfPresent(key);
    }

    /**移除Token*/
    public static void removeToken(String key){
        TOKEN_CACHE.invalidate(key);
        TOKEN_CACHE.cleanUp();
    }


}
