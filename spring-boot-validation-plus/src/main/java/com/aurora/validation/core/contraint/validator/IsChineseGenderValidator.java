package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsChineseGender;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsChineseGenderValidator implements ConstraintValidator<IsChineseGender, String> {

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        return "男".equals(gender) || "女".equals(gender) || "保密".equals(gender);
    }

}
