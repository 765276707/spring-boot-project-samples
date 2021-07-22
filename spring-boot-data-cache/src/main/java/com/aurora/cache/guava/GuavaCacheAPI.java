package com.aurora.cache.guava;

import com.google.common.cache.*;

public class GuavaCacheAPI {

    /**
     * 软引用和弱引用设置
     */
    public void weakAndSoft() {
        Cache<String, Object> cache = CacheBuilder.newBuilder()
                // key弱引用，GC时候删除
                .weakKeys()
                // value弱引用，GC时候删除
                .weakValues()
                // value软引用，OOM时候删除
                .softValues()
                // 初始容量
                .initialCapacity(10)
                .maximumSize(10000)
                .build();
    }


    /**
     * 统计访问信息
     */
    public CacheStats stats() {
        Cache<String, Object> cache = CacheBuilder.newBuilder()
                // 记录统计信息
                .recordStats()
                .maximumSize(10000)
                .build();
        return cache.stats();
    }


    /**
     * RemovalListener监听
     */
    public void removalListener() {
        Cache<String, Object> cache = CacheBuilder.newBuilder()
                // 删除监听
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
                        System.out.println(removalNotification.getKey());
                    }
                })
                .maximumSize(10000)
                .build();
    }

}
