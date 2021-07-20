package com.aurora.security.samples.service.impl;

import com.aurora.security.core.model.Authority;
import com.aurora.security.core.model.User;
import com.aurora.security.samples.domain.AccountLoginDTO;
import com.aurora.security.samples.exception.ServiceException;
import com.aurora.security.samples.service.BaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class BaseAuthServiceImpl implements BaseAuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User executeLogin(AccountLoginDTO accountLoginDTO) {
        // 登录账号密码
        String username = accountLoginDTO.getUsername();
        String password = accountLoginDTO.getPassword();
        // 模拟校验账号密码
        if (!"user001".equals(username) || !"123456".equals(password)) {
            throw new ServiceException("您的账号或密码错误");
        }
        // 查询用户权限
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("perm:select"));
        authorities.add(new Authority("perm:insert"));
        // 返回结果
        return new User("user001", "123456", authorities);
    }

}
