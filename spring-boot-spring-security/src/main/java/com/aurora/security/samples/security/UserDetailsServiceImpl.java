package com.aurora.security.samples.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 查询登录用户信息
 * @author xzbcode
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsStorage userDetailsStorage;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从缓存中查询在线用户信息
        return userDetailsStorage.getUserDetails(username);
    }

}
