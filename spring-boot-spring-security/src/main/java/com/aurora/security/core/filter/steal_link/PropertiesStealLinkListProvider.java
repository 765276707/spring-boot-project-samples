package com.aurora.security.core.filter.steal_link;

import com.aurora.security.core.model.ListType;
import com.aurora.security.core.properties.SecurityProperties;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;

/**
 * 配置式防盗链白名单提供者
 * @author xzbcode
 */
public class PropertiesStealLinkListProvider implements IStealLinkListProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ListType getType() {
        // 默认使用白名单
        return securityProperties.getStealLink().getListType();
    }

    @Override
    public Set<String> getWhiteList() {
        return securityProperties.getStealLink().getWhiteList();
    }

    @Override
    public Set<String> getBlackList() {
        return securityProperties.getStealLink().getBlackList();
    }

}
