package com.aurora.dds.service.impl;

import com.aurora.dds.entity.Area;
import com.aurora.dds.mapper.AreaMapper;
import com.aurora.dds.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public Area getArea(Integer id) {
        return areaMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void addArea(Area area) {
        // 模拟事务异常
        if (area.getId() != null) {
            throw new RuntimeException("业务异常了，事务请回滚");
        }
        areaMapper.insertSelective(area);
    }

}
