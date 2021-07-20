package com.aurora.dds.service;

import com.aurora.dds.entity.Area;

/**
 * 区域业务
 * @author xzbcode
 */
public interface AreaService {

    /**
     * 查询区域
     * @param id 区域编号
     * @return
     */
    Area getArea(Integer id);

    /**
     * 新增地区
     * @param area 地区参数
     */
    void addArea(Area area);

}
