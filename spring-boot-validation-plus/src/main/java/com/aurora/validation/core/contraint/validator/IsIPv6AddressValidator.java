package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsIPv6Address;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsIPv6AddressValidator implements ConstraintValidator<IsIPv6Address, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.IPV6, s);
    }

}
