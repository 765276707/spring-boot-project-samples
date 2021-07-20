package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsURL;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsURLValidator implements ConstraintValidator<IsURL, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.URL, s);
    }

}
