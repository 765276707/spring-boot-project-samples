package com.aurora.cache.caffeine;

import com.github.benmanes.caffeine.cache.*;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Caffeine缓存常用API
 * @author xzbcode
 */
public class CaffeineCacheAPI {

    /**
     * 软引用和弱引用设置
     */
    public void weakAndSoft() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                // key弱引用，GC时候删除
                .weakKeys()
                // value弱引用，GC时候删除
                .weakValues()
                // value软引用，OOM时候删除
                .softValues()
                .maximumSize(10000)
                .build();
    }


    /**
     * 统计访问信息
     */
    public CacheStats stats() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                // 记录统计信息
                .recordStats()
                .maximumSize(10000)
                .build();
        return cache.stats();
    }

    /**
     * 重写写入和删除规则
     */
    public void cacheWriter() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                // 写入和删除规则，可以在区分写入数据的时候使用
                .writer(new CacheWriter<String, Object>() {
                    @Override
                    public void write(@NonNull String s, @NonNull Object o) {
                        // 写入规则
                    }

                    @Override
                    public void delete(@NonNull String s, @Nullable Object o, @NonNull RemovalCause removalCause) {
                        // 删除规则
                    }
                })
                .maximumSize(10000)
                .build();
    }

    /**
     * RemovalListener监听
     */
    public void removalListener() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                // 删除监听
                .removalListener(new RemovalListener<String, Object>() {
                    @Override
                    public void onRemoval(@Nullable String s, @Nullable Object o, @NonNull RemovalCause removalCause) {
                        // 删除时触发

                    }
                })
                .maximumSize(10000)
                .build();
    }

}
