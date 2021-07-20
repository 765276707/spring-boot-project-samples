package com.aurora.dds.datasource;

import com.aurora.dds.entity.Tenant;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * 数据源管理器
 * @author xzbcode
 */
@SuppressWarnings("all")
@Component("dataSourceManager")
public class DataSourceManager {

    // 默认的数据源配置参数
    @Value("${spring.datasource.username}")
    private String defaultDbUsername;

    @Value("${spring.datasource.password}")
    private String defaultDbPassword;

    @Value("${spring.datasource.url}")
    private String defaultDbURL;

    // 默认数据源
    public static DataSource defaultDataSource = null;

    // MySQL数据源驱动
    public final static String driverClassName = "com.mysql.cj.jdbc.Driver";

    /**
     * 初始化默认的数据源
     * @apiNote 该数据源用来管理所有租户
     */
    @PostConstruct
    public void initDefaultDataSource() {
        defaultDataSource = this.getDefaultDataSource();
    }

    /**
     * 从默认库中查询所有租户
     * @return
     */
    public List<Tenant> getAllTenant() {
        String querySql = "select id, tenant_name, tenant_code, data_source_url, " +
                                "data_source_username, data_source_password, is_active from tenant";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(defaultDataSource);
        return jdbcTemplate.query(querySql, new BeanPropertyRowMapper<>(Tenant.class));
    }

    /**
     * 从默认库中查询所有可用租户
     * @return
     */
    public List<Tenant> getAllActiveTenant() {
        String querySql = "select id, tenant_name, tenant_code, data_source_url, " +
                "data_source_username, data_source_password, is_active from tenant where is_active = 1";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(defaultDataSource);
        return jdbcTemplate.query(querySql, new BeanPropertyRowMapper<>(Tenant.class));
    }

    /**
     * 获取租户管理库的数据源
     * @return
     */
    public DataSource getDefaultDataSource() {
        HikariDataSource hds = new HikariDataSource();
        hds.setUsername(defaultDbUsername);
        hds.setPassword(defaultDbPassword);
        hds.setDriverClassName(driverClassName);
        hds.setJdbcUrl(defaultDbURL);
        return hds;
    }

    /**
     * 获取该租户的数据源
     * @param tenant 租户
     * @return
     */
    public DataSource getTenantDataSource(Tenant tenant) {
        HikariDataSource hds = new HikariDataSource();
        hds.setUsername(tenant.getDataSourceUsername());
        hds.setPassword(tenant.getDataSourcePassword());
        hds.setDriverClassName(driverClassName);
        hds.setJdbcUrl(tenant.getDataSourceUrl());
        return hds;
    }
}
