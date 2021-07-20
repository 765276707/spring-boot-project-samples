package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsMoney;
import com.aurora.validation.core.contraint.regex.PatternPool;
import com.aurora.validation.core.contraint.regex.RegexMatcher;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class IsMoneyValidator implements ConstraintValidator<IsMoney, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal money, ConstraintValidatorContext constraintValidatorContext) {
        return RegexMatcher.isMatch(PatternPool.MONEY, money.toString());
    }
}
