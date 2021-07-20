package com.aurora.security.core.util;

import com.aurora.security.core.model.Authority;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Security 上下文环境工具类
 * @author xzb
 */
public class SecurityContextUtil {

    /**
     * 获取当前登录的用户名
     * @return
     */
    public static String getPrincipal() {
        String principal = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            principal = String.valueOf(authentication.getPrincipal());
        }
        return principal;
    }


    /**
     * 权限类型转换
     * @param permissions 权限字符串集合
     * @return
     */
    public static Set<Authority> convertToGrantedAuthorities(@NonNull List<String> permissions) {
        return permissions
                .stream()
                .map(perm -> {
                    return new Authority(perm);
                })
                .collect(Collectors.toSet());
    }


}
