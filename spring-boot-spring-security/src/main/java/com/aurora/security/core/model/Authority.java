package com.aurora.security.core.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * 权限对象
 * @author xzbcode
 */
public class Authority implements GrantedAuthority {

    private String authority;

    public Authority() {}

    public Authority(String authority) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
