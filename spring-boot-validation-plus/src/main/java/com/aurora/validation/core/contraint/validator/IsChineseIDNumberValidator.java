package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsChineseIDNumber;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsChineseIDNumberValidator implements ConstraintValidator<IsChineseIDNumber, String> {
    @Override
    public boolean isValid(String content, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.CHINESE_ID_NUMBER, content);
    }
}
