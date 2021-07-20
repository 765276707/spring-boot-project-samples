package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsEqualFieldValue;
import com.aurora.validation.core.contraint.util.ReflectUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsEqualFieldValueValidator implements ConstraintValidator<IsEqualFieldValue, Object> {

    private String targetField;
    private String sourceField;

    @Override
    public void initialize(IsEqualFieldValue constraintAnnotation) {
        this.targetField = constraintAnnotation.targetField();
        this.sourceField = constraintAnnotation.sourceField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object targetValue = ReflectUtil.getFieldValue(targetField, object);
            Object sourceValue = ReflectUtil.getFieldValue(sourceField, object);
            // 都为null
            if (targetValue==null && sourceValue==null) {
                return true;
            }
            // 判断是否一致
            return targetValue!=null && targetValue.equals(sourceValue);
        } catch (Exception e) {
            return false;
        }
    }

}
