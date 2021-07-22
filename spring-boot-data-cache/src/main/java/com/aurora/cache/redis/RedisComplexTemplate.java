package com.aurora.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisComplexTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 遍历所有符合条件的 Key
     * @param matchPattern key的匹配条件
     * @return
     */
    public Set<String> scanAllKeys(@NonNull String matchPattern) {
        ScanOptions options = new ScanOptions.ScanOptionsBuilder().match(matchPattern).count(Long.MAX_VALUE).build();
        RedisConnection conn = this.getConnection();
        Set<String> keySet = new HashSet<>();
        Cursor<byte[]> cursor = null;
        try {
            cursor = conn.scan(options);
            while (cursor.hasNext()) {
                keySet.add(new String(cursor.next()));
            }
        } finally {
            if (cursor != null) {
                try {
                    // 使用完主动关闭游标
                    cursor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return keySet;
    }


    /**
     * 统计所有符合条件的 Key 的数量
     * @param matchPattern key的匹配条件
     * @return
     */
    public Long scanCount(String matchPattern) {
        ScanOptions options = new ScanOptions.ScanOptionsBuilder().match(matchPattern).count(Long.MAX_VALUE).build();
        RedisConnection conn = this.getConnection();
        long index = 0;
        Cursor<byte[]> cursor = null;
        try {
            cursor = conn.scan(options);
            while (cursor.hasNext()) {
                index ++;
                cursor.next();
            }
        } finally {
            if (cursor != null) {
                try {
                    // 使用完主动关闭游标
                    cursor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return index;
    }


    /**
     * 分页查询所有符合条件的 Key 的列表
     * @param pageNum 页码，默认为 1
     * @param pageSize 每页大小，默认为 10
     * @param matchPattern key的匹配条件
     * @return
     */
    public Set<Object> scanPageList(Integer pageNum, Integer pageSize, String matchPattern) {
        ScanOptions options = new ScanOptions.ScanOptionsBuilder().match(matchPattern).count(Long.MAX_VALUE).build();
        RedisConnection conn = this.getConnection();
        Cursor<byte[]> cursor = null;
        Set<Object> resultSet = new HashSet<>();
        int index = 0;
        int startIndex = (pageNum - 1) * pageSize;
        try {
            cursor = conn.scan(options);
            while (cursor.hasNext()) {
                String key = new String(cursor.next());
                if (index >= startIndex) {
                    Object value = redisTemplate.opsForValue().get(key);
                    resultSet.add(value);
                }
                if (resultSet.size() == pageSize) {
                    break;
                }
                index ++;
            }
        } finally {
            if (cursor != null) {
                try {
                    // 使用完主动关闭游标
                    cursor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultSet;
    }


    /**
     * 通过管道技术进行批量新增
     * @param dataMap
     * @param expire
     * @param timeUnit
     */
    @SuppressWarnings("unchecked")
    public void pipelineSave(Map<String, Object> dataMap, long expire, TimeUnit timeUnit) {
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        RedisSerializer valueSerializer = redisTemplate.getDefaultSerializer();
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                dataMap.forEach((key, value) -> {
                    redisConnection.set(keySerializer.serialize(key), valueSerializer.serialize(value),
                            Expiration.from(expire, timeUnit), RedisStringCommands.SetOption.UPSERT);
                });
                return null;
            };
        });
    }


    /**
     * 获取 Redis 链接
     * @return
     */
    private RedisConnection getConnection() {
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        if (factory == null) {
            throw new RedisConnectionFailureException("try to get redis connection failure.");
        }
        return factory.getConnection();
    }

}
