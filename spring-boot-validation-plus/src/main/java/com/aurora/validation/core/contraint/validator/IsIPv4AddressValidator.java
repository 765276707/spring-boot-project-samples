package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsIPv4Address;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsIPv4AddressValidator implements ConstraintValidator<IsIPv4Address, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.IPV4, s);
    }

}
