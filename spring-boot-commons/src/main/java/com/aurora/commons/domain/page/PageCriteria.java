package com.aurora.commons.domain.page;

/**
 * <h1>分页查询条件</h1>
 * @author xzb
 */
public class PageCriteria implements ICriteria {

    /** 当前页数 */
    private int pageNum = 1;
    /** 每页大小 */
    private int pageSize = 10;
    // 排序字段
    private String sortField = "";
    // 排序类型
    private String sortOrder = "ASC";

    public PageCriteria() {
    }

    public PageCriteria(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageCriteria(int pageNum, int pageSize, String sortField, String sortOrder) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public Integer getPageNum() {
        return this.pageNum;
    }

    @Override
    public Integer getPageSize() {
        return this.pageSize;
    }

    @Override
    public String getSortField() {
        return this.sortField;
    }

    @Override
    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
