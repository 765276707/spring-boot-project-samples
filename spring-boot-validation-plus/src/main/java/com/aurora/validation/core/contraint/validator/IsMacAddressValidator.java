package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsMacAddress;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMacAddressValidator implements ConstraintValidator<IsMacAddress, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.MAC_ADDRESS, s);
    }

}
