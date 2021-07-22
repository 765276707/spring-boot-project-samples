package com.aurora.commons.domain.page;

import com.aurora.commons.utils.reflect.ReflectUtil;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 分页结果
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDetail<T> {

    /** 当前页数 */
    private int pageNum;
    /** 每页大小 */
    private int pageSize;
    /** 总条目数 */
    private long total;
    /** 结果集  */
    private Collection<T> list = new ArrayList<>();

    public PageDetail(PageInfo<T> pageInfo) {
        this.setPageNum(pageInfo.getPageNum());
        this.setPageSize(pageInfo.getPageSize());
        this.setTotal(pageInfo.getTotal());
        this.setList(pageInfo.getList());
    }

    public <R> PageDetail(PageInfo<R> pageInfo, @NonNull Class<T> targetClass) {
        // 集合类型转换
        Collection<T> collect = pageInfo.getList()
                .stream()
                .map(source -> ReflectUtil.convertClassType(source, targetClass))
                .collect(Collectors.toList());
        this.setPageNum(pageInfo.getPageNum());
        this.setPageSize(pageInfo.getPageSize());
        this.setTotal(pageInfo.getTotal());
        this.setList(collect);
    }

}
