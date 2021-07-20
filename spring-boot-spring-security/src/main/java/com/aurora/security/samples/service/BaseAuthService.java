package com.aurora.security.samples.service;

import com.aurora.security.core.model.User;
import com.aurora.security.samples.domain.AccountLoginDTO;

/**
 * 模拟登录业务
 * @author xzbcode
 */
public interface BaseAuthService {

    /**
     * 账号密码登录
     * @param accountLoginDTO 账号密码参数
     * @return User
     */
    User executeLogin(AccountLoginDTO accountLoginDTO);

}
