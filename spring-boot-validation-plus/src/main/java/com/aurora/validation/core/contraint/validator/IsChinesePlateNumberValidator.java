package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsChinesePlateNumber;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsChinesePlateNumberValidator implements ConstraintValidator<IsChinesePlateNumber, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.CHINESE_PLATE_NUMBER, s);
    }

}
