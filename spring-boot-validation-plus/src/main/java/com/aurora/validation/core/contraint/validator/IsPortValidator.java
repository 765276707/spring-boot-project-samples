package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsPort;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPortValidator implements ConstraintValidator<IsPort, Integer> {

    @Override
    public boolean isValid(Integer port, ConstraintValidatorContext constraintValidatorContext) {
        return port >= 0 && port <= 65535;
    }

}
