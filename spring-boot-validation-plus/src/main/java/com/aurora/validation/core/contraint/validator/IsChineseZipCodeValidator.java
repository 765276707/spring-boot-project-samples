package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsChineseZipCode;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsChineseZipCodeValidator implements ConstraintValidator<IsChineseZipCode, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.CHINESE_ZIP_CODE, s);
    }

}
