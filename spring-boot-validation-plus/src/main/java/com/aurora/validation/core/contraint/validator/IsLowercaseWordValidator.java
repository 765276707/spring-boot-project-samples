package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsLowercaseWord;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsLowercaseWordValidator implements ConstraintValidator<IsLowercaseWord, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.LOWERCASE_WORD, s);
    }

}
