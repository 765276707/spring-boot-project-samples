package com.aurora.validation.core.password;

import org.passay.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 密码校验规则
 * @author xzbcode
 */
class PasswordRule {

    /**
     * 获取校验规则
     * @return
     */
    static List<Rule> getRules(PasswordComplexity complexity) {
        List<Rule> rules;
        switch (complexity) {
            case SIMPLE:
                rules = Arrays.asList(
                        new LengthRule(6, 20),
                        // 不允许连续6个字母字符
                        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 4, false),
                        // 不允许连续6个数字字符
                        new IllegalSequenceRule(EnglishSequenceData.Numerical, 4, false),
                        // 不允许有空白字符
                        new WhitespaceRule()
                );
                break;

            case MEDIUM:
                rules = Arrays.asList(
                        new LengthRule(8, 20),
                        // 至少包含一个大写，一个小写，一个数字
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        // 不允许连续6个字母字符
                        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 6, false),
                        // 不允许连续6个数字字符
                        new IllegalSequenceRule(EnglishSequenceData.Numerical, 6, false),
                        // 不允许有空白字符
                        new WhitespaceRule()
                );
                break;

            case COMPLEX:
                rules = Arrays.asList(
                        new LengthRule(10, 20),
                        // 至少包含一个大写，一个小写，一个数字，一个特殊字符
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        // 不允许连续8个字母字符
                        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 8, false),
                        // 不允许连续8个数字字符
                        new IllegalSequenceRule(EnglishSequenceData.Numerical, 8, false),
                        // 不允许连续8个键盘字符
                        new IllegalSequenceRule(EnglishSequenceData.USQwerty, 8, false),
                        // 不允许有空白字符
                        new WhitespaceRule()
                );
                break;

            default:
                rules = Collections.emptyList();
        }
        return rules;
    }

}
