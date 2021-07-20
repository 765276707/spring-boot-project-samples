package com.aurora.dds.service;

import com.aurora.dds.entity.Tenant;
import java.util.List;

/**
 * 租户业务
 * @author xzbcode
 */
public interface TenantService {

    /**
     * 查询所有可用租户
     * @return
     */
    List<Tenant> getAllActiveTenant();

}
