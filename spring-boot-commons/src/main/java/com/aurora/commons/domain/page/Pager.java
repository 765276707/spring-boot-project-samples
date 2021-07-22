package com.aurora.commons.domain.page;

import com.github.pagehelper.PageHelper;

import javax.persistence.Column;
import javax.validation.ValidationException;
import java.lang.reflect.Field;

/**
 * <h1>对PageHelper补充</h1>
 * @author xzb
 */
public class Pager {

    /**
     * 开始分页
     * @param criteria 分页参数
     */
    public static void startPage(ICriteria criteria) {
        if (!criteria.hasOrderBy()) {
            // 未携带排序参数
            PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        } else {
            PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize(), criteria.getOrderBy());
        }
    }

    /**
     * 验证排序参数后开始分页
     * @param criteria 分页参数
     * @param sortFieldClass 排序字段类
     */
    public static void startPage(ICriteria criteria, Class<?> sortFieldClass) {
        if (!criteria.hasOrderBy()) {
            // 未携带排序参数
            PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        } else {
            // 校验排序字段是否合法
            if (!containField(sortFieldClass, criteria.getSortField())) {
                throw new ValidationException("不合法的排序参数");
            }
            PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize(), criteria.getOrderBy());
        }
    }


    /**
     * <h2>验证排序参数后开始分页</h2>
     * @param criteria 分页参数
     * @param sortFields 排序字段数组
     */
    public static void startPage(ICriteria criteria, String[] sortFields) {
        if (!criteria.hasOrderBy()) {
            // 未携带排序参数
            PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        } else {
            // 校验排序字段是否合法
            if (!contain(sortFields, criteria.getSortField())) {
                throw new ValidationException("不合法的排序参数");
            }
            PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize(), criteria.getOrderBy());
        }
    }

    /**
     * <h2>判断排序字段是否在对应的实体类中，
     *      驼峰模式时会根据注解获取数据库表的真实字段名</h2>
     * @param clazz 实体类
     * @param sortFieldName 排序字段名
     * @return
     */
    public static boolean containField(Class<?> clazz, String sortFieldName) {
        if (sortFieldName==null || "".equals(sortFieldName)) {
            return false;
        }
        // 当目标类的父类为null，则说明其已经是 Object.class
        while (clazz!=null && clazz.getSuperclass()!=null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Column columnAnno = field.getAnnotation(Column.class);
                if (columnAnno != null) {
                    if (sortFieldName.equals(columnAnno.name())) {
                        return true;
                    }
                } else {
                    if (sortFieldName.equals(field.getName())) {
                        return true;
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }


    /**
     * 数组中是否含有某个元素
     * @param sources 数组
     * @param element 元素
     * @return
     */
    private static boolean contain(String[] sources, String element) {
        for (String source : sources) {
            if (element.equals(source)) {
                return true;
            }
        }
        return false;
    }
}
