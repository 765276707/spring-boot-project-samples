package com.aurora.validation.core.contraint.validator;

import com.aurora.validation.core.contraint.annotation.IsAssignableFrom;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsAssignableFromValidator implements ConstraintValidator<IsAssignableFrom, String> {

    private Class<?> targetClazz;

    @Override
    public void initialize(IsAssignableFrom constraintAnnotation) {
        this.targetClazz = constraintAnnotation.targetClazz();
    }

    @Override
    public boolean isValid(String originClazzPath, ConstraintValidatorContext constraintValidatorContext) {
        if (originClazzPath==null || "".equals(originClazzPath)) {
            return false;
        }
        // 检查是否为目标类的可继承类
        Class clazz = null;
        try {
            clazz = Class.forName(originClazzPath);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return this.targetClazz.isAssignableFrom(clazz);
    }

}
