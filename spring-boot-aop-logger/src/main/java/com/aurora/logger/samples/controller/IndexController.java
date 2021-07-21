package com.aurora.logger.samples.controller;

import com.aurora.logger.core.annotation.AopLogger;
import com.aurora.logger.core.service.OperateType;
import com.aurora.logger.samples.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟记录日志
 * @author xzbcode
 */
@RestController
public class IndexController {

    /**
     * 模拟查询用户
     * @return
     */
    @AopLogger(desc = "模拟查询用户", type = OperateType.SEARCH)
    @GetMapping("/user")
    public User getUser() {
        return new User(1, "user001", 1, "男", 2, "无效");
    }

}
