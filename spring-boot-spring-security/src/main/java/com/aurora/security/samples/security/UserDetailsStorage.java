package com.aurora.security.samples.security;

import com.aurora.security.core.model.User;
import com.aurora.security.core.properties.SecurityProperties;
import com.aurora.security.samples.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * <已登录的用户信息管理
 * @author xzbcode
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class UserDetailsStorage {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SecurityProperties securityProperties;

    // 令牌在缓存中的KEY
    private static final String TOKEN_PREFIX_IN_CACHE = "security:user:";

    /**
     * 获取已登录用户信息
     * @param username 用户名
     * @return
     */
    public UserDetails getUserDetails(String username) {
        // 从缓存中查询在线用户信息
        String tokenKey = getKeyInCache(username);
        Object cacheUser = redisTemplate.opsForValue().get(tokenKey);
        return cacheUser==null ? null : (User) cacheUser;
    }

    /**
     * 存储已登录用户信息
     * @param user 已登录的用户
     */
    public void storeUserDetails(User user) {
        // 获取配置的访问令牌的过期时间，单位: 秒
        Integer accessTokenExpire = securityProperties.getToken().getAccessTokenExpire();
        // 存入缓存
        String tokenKey = getKeyInCache(user.getUsername());
        try {
            redisTemplate.opsForValue().set(tokenKey, user, accessTokenExpire, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("store user details failure, cause by {}", e.getMessage());
            throw new ServiceException("保存登录用户信息失败，请重新尝试");
        }
    }

    /**
     * 删除已登录用户信息
     * @param username
     */
    public void removeUserDetails(String username) {
        redisTemplate.delete(getKeyInCache(username));
    }


    private String getKeyInCache(@NonNull String username) {
        return TOKEN_PREFIX_IN_CACHE + username;
    }
}
