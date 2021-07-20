package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsChinesePhoneNumber;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsChinesePhoneNumberValidator implements ConstraintValidator<IsChinesePhoneNumber, String> {

    @Override
    public boolean isValid(String content, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.CHINESE_PHONE_NUMBER, content);
    }

}
