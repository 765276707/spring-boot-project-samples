package com.aurora.dds.datasource;

import com.aurora.dds.entity.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源
 * @author xzbcode
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Resource
    private DataSourceManager dataSourceManager;

    // 数据源池
    private final static Map<Object, Object> dataSourcePool = new HashMap<>();

    /**
     * 查询租户信息
     * @apiNote SpringBean初始化后执行
     */
    @Override
    public void afterPropertiesSet() {
        // 查询所有可用租户
        List<Tenant> tenants = dataSourceManager.getAllActiveTenant();
        for (Tenant tenant : tenants) {
            if (tenant != null) {
                // 生成数据源
                DataSource ds = dataSourceManager.getTenantDataSource(tenant);
                // 放到数据源池
                dataSourcePool.put(tenant.getTenantCode(), ds);
                // 放到租户池
                DataSourceHolder.putTenantPool(tenant);
            }
        }
    }

    /**
     * 设置数据源的查询KEY
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        Tenant tenant = DataSourceHolder.get();
        return tenant==null ? null : tenant.getTenantCode();
    }

    /**
     * 确定当前的数据源
     * @return
     */
    @Override
    protected DataSource determineTargetDataSource() {
        Object currentDsKey = determineCurrentLookupKey();
        if (log.isDebugEnabled()) {
            log.debug("当前选择的数据源KEY是： {}", currentDsKey);
        }
        if (currentDsKey == null) {
            // 未选中数据源，则使用默认数据源
            return dataSourceManager.defaultDataSource;
        }
        return (DataSource) dataSourcePool.get(String.valueOf(currentDsKey));
    }

    /**
     * 设置数据源对象池
     * @param targetDataSources
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(dataSourcePool);
    }

}
