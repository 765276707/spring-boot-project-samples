package com.aurora.dds.service.impl;

import com.aurora.dds.entity.Tenant;
import com.aurora.dds.mapper.TenantMapper;
import com.aurora.dds.service.TenantService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TenantServiceImpl implements TenantService {

    @Resource
    private TenantMapper tenantMapper;

    @Override
    public List<Tenant> getAllActiveTenant() {
        // 构建查询条件
        Example example = Example.builder(Tenant.class)
            .where(Sqls.custom().andEqualTo("isActive", true))
            .build();
        // 查询数据库
        return tenantMapper.selectByExample(example);
    }

}
