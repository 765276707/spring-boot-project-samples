package com.aurora.validation.samples.controller;

import com.aurora.validation.core.contraint.group.Insert;
import com.aurora.validation.core.contraint.group.Update;
import com.aurora.validation.core.password.PasswordChecker;
import com.aurora.validation.core.password.PasswordComplexity;
import com.aurora.validation.core.sensitive.ISensitiveWordDataSource;
import com.aurora.validation.core.sensitive.MatchRule;
import com.aurora.validation.core.sensitive.SensitiveWordChecker;
import com.aurora.validation.samples.response.ResultResponse;
import com.aurora.validation.samples.domain.VipMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


/**
 * 校验测试控制器
 * @author xzbcode
 */
@RestController
@RequestMapping("/vipMember")
@Slf4j
public class IndexController {

    @Resource
    private ISensitiveWordDataSource sensitiveWordDataSource;

    /**
     * 添加会员
     * @param vipMember 会员参数
     * @return
     */
    @PostMapping("/add")
    public ResultResponse addMember(@Validated(Insert.class) @RequestBody VipMember vipMember) {
        // 打印参数
        log.info("==> 新增会员参数详情: {}", vipMember.toString());
        // 校验密码
        validPassword(vipMember.getPassword());
        // 校验敏感词
        validSensitiveWord(vipMember.getSensitiveWord());
        // 响应结果
        return ResultResponse.success("添加会员成功");
    }

    /**
     * 更新会员
     * @param vipMember 会员参数
     * @return
     */
    @PutMapping("/update")
    public ResultResponse updateMember(@Validated(Update.class) @RequestBody VipMember vipMember) {
        // 打印参数
        log.info("==> 编辑会员参数详情: {}", vipMember.toString());
        // 校验密码
        validPassword(vipMember.getPassword());
        // 校验敏感词
        validSensitiveWord(vipMember.getSensitiveWord());
        // 响应结果
        return ResultResponse.success("更新会员成功");
    }

    /**
     * 校验密码及其强度
     * @param password 密码
     */
    private void validPassword(String password) {
        PasswordChecker.isValid(password, PasswordComplexity.MEDIUM, "密码强度较低");
    }

    /**
     * 校验敏感词
     * @param sensitiveWord 敏感词内容
     */
    private void validSensitiveWord(String sensitiveWord) {
        SensitiveWordChecker.contain(sensitiveWord, MatchRule.MIN_DEPTH, sensitiveWordDataSource);
    }
}
