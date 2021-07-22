package com.aurora.commons.domain.page;

import org.springframework.util.StringUtils;

public interface ICriteria {

    Integer getPageNum();

    Integer getPageSize();

    String getSortField();

    String getSortOrder();

    /**
     * <h2>是否需要排序</h2>
     * @return
     */
    default boolean hasOrderBy() {
        return !StringUtils.isEmpty(getSortField())
                && !StringUtils.isEmpty(getSortOrder())
                && ("ASC".equalsIgnoreCase(getSortOrder())
                || "DESC".equalsIgnoreCase(getSortOrder()));
    }

    /**
     * <h2>获取组合后的排序sql条件</h2>
     * @return id asc
     */
    default String getOrderBy() {
        return getSortField() + " " + getSortOrder();
    }

}
