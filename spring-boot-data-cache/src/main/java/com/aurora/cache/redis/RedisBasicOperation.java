package com.aurora.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis基础操作
 * @author xzbcode
 */

@Component
public class RedisBasicOperation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RedisTemplate redisTemplate;

    private final static String KEY = "user:id_001";

    public void get() {
        Object cache = redisTemplate.opsForValue().get(KEY);
        logger.info(cache==null?"未查询到用户":"查询到用户："+ ((User) cache).toString());
    }

    public void set() {
        User user = new User(1, "user001", "123456");
        redisTemplate.opsForValue().set(KEY, user);
    }

    public void expire() {
        // 30秒失效
        redisTemplate.expire(KEY, 30, TimeUnit.SECONDS);
    }

    public void delete() {
        redisTemplate.delete(KEY);
    }

    /**
     * 用户，测试用
     */
    class User {

        private Integer id;

        private String username;

        private String password;

        public User() {
        }

        public User(Integer id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
