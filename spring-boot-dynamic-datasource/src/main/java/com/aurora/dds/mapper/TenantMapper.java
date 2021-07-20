package com.aurora.dds.mapper;

import com.aurora.dds.entity.Tenant;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TenantMapper extends Mapper<Tenant> {
}