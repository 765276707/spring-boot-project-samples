package com.aurora.security.samples.controller;

import com.aurora.security.core.filter.authc.JwtManager;
import com.aurora.security.core.model.Token;
import com.aurora.security.core.model.User;
import com.aurora.security.core.response.ResultResponse;
import com.aurora.security.samples.domain.AccountLoginDTO;
import com.aurora.security.samples.security.UserDetailsStorage;
import com.aurora.security.samples.service.BaseAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 权限框架模拟测试接口
 * @author xzbcode
 */
@Slf4j
@RestController
public class IndexController {

    @Autowired
    private BaseAuthService baseAuthService;
    @Autowired
    private JwtManager jwtManager;
    @Autowired
    private UserDetailsStorage userDetailsStorage;

    /**
     * 用户登录
     * @param accountLoginDTO 账号密码登录参数
     * @return
     */
    @PostMapping("/login")
    public ResultResponse<Token> login(@RequestBody @Validated AccountLoginDTO accountLoginDTO) {
        // 查询并验证用户
        User user = baseAuthService.executeLogin(accountLoginDTO);
        // 创建令牌
        Token token = jwtManager.createToken(user);
        // 保存登录用户信息
        userDetailsStorage.storeUserDetails(user);
        // 响应结果
        return ResultResponse.success("登录成功", token);
    }

    /**
     * 测试权限
     * 已拥有 perm:select
     * @return
     */
    @PreAuthorize("hasAuthority('perm:select')")
    @PostMapping("/testSelectPermission")
    public ResultResponse testSelectPermission() {
        // 响应结果
        return ResultResponse.success("成功访问了 testSelectPermission");
    }

    /**
     * 测试权限
     * 未拥有 perm:update
     * @return
     */
    @PreAuthorize("hasAuthority('perm:update')")
    @PostMapping("/testUpdatePermission")
    public ResultResponse testUpdatePermission() {
        // 响应结果
        return ResultResponse.success("成功访问了 testUpdatePermission");
    }

    /**
     * 测试限流
     * @return
     */
    @PostMapping("/testRateLimit")
    public ResultResponse testRateLimit() {
        // 响应结果
        return ResultResponse.success("成功访问了 testRateLimit");
    }

    /**
     * 测试防重放攻击
     * @return
     */
    @PostMapping("/testReplayAttack")
    public ResultResponse testReplayAttack() {
        // 响应结果
        return ResultResponse.success("成功访问了 testReplayAttack");
    }

    /**
     * 测试防盗链攻击
     * @return
     */
    @PostMapping("/testStealLink")
    public ResultResponse testStealLink() {
        // 响应结果
        return ResultResponse.success("成功访问了 testStealLink");
    }

    /**
     * 测试XSS攻击
     * param示例:
     * @return
     */
    @GetMapping("/testXssAttack")
    public ResultResponse testXssAttack(@RequestParam("param") String param) {
        log.info("param: {}", param);
        // 响应结果
        return ResultResponse.success("成功访问了 testXssAttack");
    }

}
