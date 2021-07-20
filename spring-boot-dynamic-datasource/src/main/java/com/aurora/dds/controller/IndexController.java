package com.aurora.dds.controller;

import com.aurora.dds.entity.Area;
import com.aurora.dds.entity.Tenant;
import com.aurora.dds.service.AreaService;
import com.aurora.dds.service.TenantService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源接口测试
 * @author xzbcode
 */
@RestController
public class IndexController {

    @Resource
    private AreaService areaService;
    @Resource
    private TenantService tenantService;


    /**
     * 模拟查询所有可用租户
     * @return
     */
    @GetMapping("/tenants")
    public List<Tenant> getAllActiveTenant() {
        return tenantService.getAllActiveTenant();
    }


    /**
     * 模拟查询区域信息
     * @param id 区域编号
     * @return
     */
    @GetMapping("/area")
    public Area getArea(@RequestParam("id") Integer id) {
        return areaService.getArea(id);
    }


    /**
     * 模拟添加区域
     * @param area 区域参数
     * @return
     */
    @PostMapping("/area")
    public Map<String, Object> addArea(@RequestBody Area area) {
        try {
            areaService.addArea(area);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 100);
            result.put("message", "添加失败");
            return result;
        }
        // 添加成功
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "添加成功");
        return result;
    }
}
