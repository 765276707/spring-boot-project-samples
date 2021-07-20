package com.aurora.dds.entity;

import javax.persistence.*;

public class Tenant {
    @Id
    private Integer id;

    @Column(name = "tenant_name")
    private String tenantName;

    @Column(name = "tenant_code")
    private String tenantCode;

    @Column(name = "data_source_url")
    private String dataSourceUrl;

    @Column(name = "data_source_username")
    private String dataSourceUsername;

    @Column(name = "data_source_password")
    private String dataSourcePassword;

    @Column(name = "is_active")
    private Boolean isActive;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return tenant_name
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * @param tenantName
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName == null ? null : tenantName.trim();
    }

    /**
     * @return tenant_code
     */
    public String getTenantCode() {
        return tenantCode;
    }

    /**
     * @param tenantCode
     */
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    /**
     * @return data_source_url
     */
    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    /**
     * @param dataSourceUrl
     */
    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl == null ? null : dataSourceUrl.trim();
    }

    /**
     * @return data_source_username
     */
    public String getDataSourceUsername() {
        return dataSourceUsername;
    }

    /**
     * @param dataSourceUsername
     */
    public void setDataSourceUsername(String dataSourceUsername) {
        this.dataSourceUsername = dataSourceUsername == null ? null : dataSourceUsername.trim();
    }

    /**
     * @return data_source_password
     */
    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    /**
     * @param dataSourcePassword
     */
    public void setDataSourcePassword(String dataSourcePassword) {
        this.dataSourcePassword = dataSourcePassword == null ? null : dataSourcePassword.trim();
    }

    /**
     * @return is_active
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}