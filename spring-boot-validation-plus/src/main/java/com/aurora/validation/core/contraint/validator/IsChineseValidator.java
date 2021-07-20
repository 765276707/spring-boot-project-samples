package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsChinese;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsChineseValidator implements ConstraintValidator<IsChinese, String> {

    @Override
    public boolean isValid(String content, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.SIMPLE_CHINESE, content);
    }

}
