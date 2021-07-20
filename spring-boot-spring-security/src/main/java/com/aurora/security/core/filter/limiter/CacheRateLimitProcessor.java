package com.aurora.security.core.filter.limiter;

import com.aurora.security.core.properties.SecurityProperties;
import com.aurora.security.core.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 基于缓存的实现
 * @author xzbcode
 */
@Slf4j
@SuppressWarnings("all")
public class CacheRateLimitProcessor implements IRateLimitProcessor {

    @Autowired
    private SecurityProperties secProp;
    @Autowired
    private RedisTemplate redisTemplate;

    // SpringSecurity中匿名用户的principal
    private final static String ANON_USER = "anonymousUser";
    // 在缓存中的前缀
    private final static String RATE_LIMIT_PREFIX = "security:rate_limit:";

    public int getRateByIp(String ip, Long interval, TimeUnit timeUnit) {
        return this.incrementAndGet(getRlCacheKey(ip, null), interval, timeUnit);
    }

    public int getRateByPrincipal(String principal, Long interval, TimeUnit timeUnit) {
        return this.incrementAndGet(getRlCacheKey(null, principal), interval, timeUnit);
    }

    @Override
    public boolean process(Authentication authentication, HttpServletRequest request) {
        long interval = this.getRateLimitProp().getInterval();
        // 认证用户采用凭证为标识
        if (authentication!=null && authentication.getPrincipal()!=null) {
            Object principal = authentication.getPrincipal();
            // 凭证为 UserDetails
            if (principal instanceof UserDetails) {
                return this.getRateLimitProp().getPrincipalRate()
                        <= this.getRateByPrincipal(((UserDetails) principal).getUsername(), interval, TimeUnit.SECONDS);
            }
            // 凭证为 username
            String username = String.valueOf(principal);
            if (!ANON_USER.equalsIgnoreCase(username)) {
                return this.getRateLimitProp().getPrincipalRate()
                        <= this.getRateByPrincipal(username, interval, TimeUnit.SECONDS);
            }
        }

        // 未认证或匿名用户采用IP为标识
        String ip = WebUtil.getClientIpAddr(request);
        if (!StringUtils.isEmpty(ip)) {
            return this.getRateByIp(ip, interval, TimeUnit.SECONDS) <= this.getRateLimitProp().getIpRate();
        }

        // 默认返回
        return true;
    }

    /**
     * <h2>获取限流属性</h2>
     * @return
     */
    private SecurityProperties.RateLimitProperties getRateLimitProp() {
        return secProp.getRateLimit();
    }

    private String getRlCacheKey(String ip, Object principal) {
        // ip
        if (ip != null) {
            return RATE_LIMIT_PREFIX + "ip:" + ip;
        }
        // principal
        return RATE_LIMIT_PREFIX + "principal:" + principal;
    }

    /**
     * 先自增后拿值
     * @param key
     * @return
     */
    private int incrementAndGet(@NonNull String key, long interval, TimeUnit timeUnit) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object cacheVal = valueOperations.get(key);
        valueOperations.increment(key);
        if (cacheVal != null) {
            return (int) cacheVal;
        } else {
            // 设置限流时间区间
            redisTemplate.expire(key, interval, timeUnit);
            return 1;
        }
    }

}
