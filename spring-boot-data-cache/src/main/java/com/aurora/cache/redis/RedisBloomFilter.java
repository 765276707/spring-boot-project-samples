package com.aurora.cache.redis;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器
 * @author xzbcode
 */
public class RedisBloomFilter {

    /**
     * 创建布隆过滤器
     * @param expectedInsertions 位图的预估容量
     * @return
     */
    public static BloomFilter<String> create(int expectedInsertions, double fpp) {
        return BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);
    }

    /**
     * 创建布隆过滤器
     * @param expectedInsertions 位图的预估容量
     * @param fpp 误判率 默认 0.03，取值区间（0.00 < fpp < 1.00）
     * @return
     */
    public static BloomFilter<String> createWithFPP(int expectedInsertions, double fpp) {
        return BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);
    }

}
