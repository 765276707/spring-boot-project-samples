package com.aurora.validation.core.password;


import org.passay.LengthComplexityRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;

import javax.validation.ValidationException;
import java.util.List;

/**
 * 密码校验器
 * @author xzbcode
 */
public class PasswordChecker {

    // 默认提示信息
    private static final String DEFAULT_ERROR_MESSAGE = "your password does not conform to rules.";

    /**
     * 校验是否符合密码规则
     * @param password 密码
     * @param errorMessage 错误提示
     */
    public static void isValid(String password, PasswordComplexity complexity, String errorMessage) {
        List<Rule> rules = PasswordRule.getRules(complexity);
        PasswordValidator passwordValidator = new PasswordValidator(rules);
        // 返回校验结果
        isValid(passwordValidator, password, errorMessage);
    }


    /**
     * 根据密码位数自动校验是否符合密码规则
     * @param password 密码
     * @param errorMessage 错误提示
     */
    public static void isValidByPwdLength(String password, String errorMessage) {
        LengthComplexityRule complexityRule = new LengthComplexityRule();
        complexityRule.addRules("[6-7]", PasswordRule.getRules(PasswordComplexity.SIMPLE));
        complexityRule.addRules("[8-9]", PasswordRule.getRules(PasswordComplexity.MEDIUM));
        complexityRule.addRules("[10-20]", PasswordRule.getRules(PasswordComplexity.COMPLEX));
        PasswordValidator passwordValidator = new PasswordValidator(complexityRule);
        // 返回校验结果
        isValid(passwordValidator, password, errorMessage);
    }


    /**
     * 校验结果
     * @param passwordValidator 密码校验器
     * @param password 密码
     * @param errorMessage 错误提示
     */
    public static void isValid(PasswordValidator passwordValidator, String password, String errorMessage) {
        // 返回校验结果
        if (!passwordValidator.validate(new PasswordData(password)).isValid()) {
            throw new ValidationException(errorMessage==null?DEFAULT_ERROR_MESSAGE:errorMessage);
        }
    }

}
