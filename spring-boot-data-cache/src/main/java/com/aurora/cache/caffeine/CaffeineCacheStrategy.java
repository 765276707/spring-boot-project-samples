package com.aurora.cache.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * 几种Caffeine缓存策略
 * @author xzbcode
 */
public class CaffeineCacheStrategy {

    /**
     * 人工加载策略
     */
    public Cache<String, Object> getCache() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                // 写入30分后失效
                .expireAfterWrite(30, TimeUnit.MINUTES)
                // 每次访问刷新30分钟
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .maximumSize(10000)
                .build();
        return cache;
    }


    /**
     * 同步加载策略
     * @return
     */
    public LoadingCache<String, Object> getLoadingCache() {
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                // 写入30分后失效
                .expireAfterWrite(30, TimeUnit.MINUTES)
                // 每次访问刷新30分钟
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .maximumSize(10000)
                .build(cacheKey -> "cacheValue");
        return cache;
    }

    /**
     * 异步加载策略
     * @return
     */
    public AsyncLoadingCache<String, Object> getAsyncLoadingCache() {
        AsyncLoadingCache<String, Object> cache = Caffeine.newBuilder()
                // 写入30分后失效
                .expireAfterWrite(30, TimeUnit.MINUTES)
                // 每次访问刷新30分钟
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .maximumSize(10000)
                .buildAsync(cacheKey -> "cacheValue");
        return cache;
    }

}
