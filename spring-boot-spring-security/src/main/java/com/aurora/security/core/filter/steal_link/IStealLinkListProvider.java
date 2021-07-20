package com.aurora.security.core.filter.steal_link;

import com.aurora.security.core.model.ListType;
import java.util.Set;

/**
 * 防盗链名单提供者接口
 * @author xzbcode
 */
public interface IStealLinkListProvider {

    /** 获取名单类型 */
    ListType getType();

    /** 白名单 */
    Set<String> getWhiteList();

    /** 黑名单 */
    Set<String> getBlackList();

}
