package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsIPAddress;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsIPAddressValidator implements ConstraintValidator<IsIPAddress, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.IPV4, s) || RegexMatcher.isMatch(PatternPool.IPV6, s);
    }

}
