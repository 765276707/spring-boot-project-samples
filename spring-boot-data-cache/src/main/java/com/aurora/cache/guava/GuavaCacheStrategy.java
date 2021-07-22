package com.aurora.cache.guava;



import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class GuavaCacheStrategy {

    /**
     * 人工加载策略
     */
    public Cache<String, Object> getCache() {
        Cache<String, Object> cache = CacheBuilder.newBuilder()
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
     */
    public LoadingCache<String, Object> getLoadingCache() {
        LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
                // 写入30分后失效
                .expireAfterWrite(30, TimeUnit.MINUTES)
                // 每次访问刷新30分钟
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .maximumSize(10000)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String cacheKey) throws Exception {
                        return "cacheValue";
                    }
                });
        return cache;
    }

    /**
     * 异步加载策略
     */
    public LoadingCache<String, Object> getAsyncLoadingCache() {
        LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
                // 写入30分后失效
                .expireAfterWrite(30, TimeUnit.MINUTES)
                // 每次访问刷新30分钟
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .maximumSize(10000)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String cacheKey) throws Exception {
                        return "cacheValue";
                    }

                    @Override
                    public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
                        return ThreadPoolProvider.getExecutorService().submit(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                return "cacheValue";
                            }
                        });
                    }
                });
        return cache;
    }
}
