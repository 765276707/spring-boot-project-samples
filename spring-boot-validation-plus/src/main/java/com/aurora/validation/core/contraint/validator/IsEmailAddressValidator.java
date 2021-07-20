package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsEmailAddress;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsEmailAddressValidator implements ConstraintValidator<IsEmailAddress, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.EMAIL, s);
    }

}
