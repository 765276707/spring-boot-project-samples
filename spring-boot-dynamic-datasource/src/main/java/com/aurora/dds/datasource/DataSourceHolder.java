package com.aurora.dds.datasource;

import com.aurora.dds.entity.Tenant;
import org.springframework.util.Assert;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据源存储器
 * @author xzbcode
 */
public class DataSourceHolder {

    // 租户池
    private final static Map<String, Tenant> tenantPool = new LinkedHashMap<>();
    // 租户线程锁
    private final static ThreadLocal<Tenant> tenantThreadLocal =  new ThreadLocal<>();

    /**
     * 设置租户到租户池
     * @param tenant 租户
     */
    public static void putTenantPool(Tenant tenant) {
        Assert.notNull(tenant, "租户不能为空");
        Assert.notNull(tenant.getTenantCode(), "租户编码不能为空");
        tenantPool.put(tenant.getTenantCode(), tenant);
    }

    /**
     * 设置租户到当前线程锁
     */
    public static void set(String tenantCode) {
        Tenant tenant = tenantPool.get(tenantCode);
        tenantThreadLocal.set(tenant);
    }

    /**
     * 从当前线程锁内获取租户
     * @return Tenant
     */
    public static Tenant get() {
        return tenantThreadLocal.get();
    }

    /**
     * 清除当前线程锁内的租户
     */
    public static void clear() {
        tenantThreadLocal.remove();
    }

}
