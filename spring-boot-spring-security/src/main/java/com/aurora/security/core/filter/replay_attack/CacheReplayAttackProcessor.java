package com.aurora.security.core.filter.replay_attack;

import com.aurora.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 基于缓存的防重放处理器
 * @author xzbcode
 */
@Slf4j
@SuppressWarnings("all")
public class CacheReplayAttackProcessor implements IReplayAttackProcessor {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SecurityProperties securityProperties;

    private static final String REPLAY_ATTACK_CACHE_PREFIX = "security:replay_attack:";

    @Override
    public boolean process(HttpServletRequest request) throws ReplayAttackException {
        // 获取配置
        SecurityProperties.ReplayAttackProperties replayProp = this.getProp();
        Long expire = replayProp.getExpire() == null ? 5*60 : replayProp.getExpire();

        // 验证时间戳
        String timestamp = request.getHeader(replayProp.getTimestampFlag());
        try {
            if (!checkQueryTimestamp(timestamp, expire)) {
                return false;
            }
        } catch (Exception e) {
            // 验证时间戳出错
            throw new ReplayAttackException(
                    String.format("An error occurred when checking query timestamp. cause by : %s", e.getMessage()));
        }

        // 验证随机数
        String trackFlag = request.getHeader(replayProp.getTrackFlag());
        try {
            if (!checkQueryTrack(trackFlag, expire)) {
                return false;
            }
        } catch (Exception e) {
            // 验证随机数出错
            throw new ReplayAttackException(
                    String.format("An error occurred when checking query track. cause by %s", e.getMessage()));
        }

        return true;
    }

    /**
     * 验证本次请求是否在有效期内
     * @param timestamp 时间戳
     * @param expire 有效时间
     * @return
     */
    private boolean checkQueryTimestamp(String timestamp, Long expire) {
        if (StringUtils.isEmpty(timestamp)) {
            return false;
        }
        long tamp = Long.parseLong(timestamp);
        long interval = System.currentTimeMillis() - tamp;
        return interval < expire * 1000;
    }

    /**
     * 验证本次请求的随机数是否有效
     * @param track 随机数
     * @param expire 有效时间
     * @return
     */
    private boolean checkQueryTrack(String track, Long expire) {
        if (StringUtils.isEmpty(track)) {
            return false;
        }
        // 从缓存中验证
        String cacheKey = REPLAY_ATTACK_CACHE_PREFIX + track;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object cacheVal = valueOperations.get(cacheKey);
        if (cacheVal != null) {
            return false;
        }
        valueOperations.set(cacheKey, track, expire, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 获取配置
     * @return
     */
    private SecurityProperties.ReplayAttackProperties getProp() {
        return this.securityProperties.getReplayAttack();
    }
}
