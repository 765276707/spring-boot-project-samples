package com.aurora.commons.utils.jsr303;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * JSR303校验工具类
 * @author xzbcode
 */
@SuppressWarnings("all")
public class ValidatorUtil {

    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * JSR303校验
     * @param obj 校验对象
     * @param groups 校验分组
     * @param <T>
     */
    public static <T> void validate(T obj, Class ...groups) {
        Set<ConstraintViolation<T>> validationSet = validator.validate(obj, groups);
        if (validationSet!=null && !validationSet.isEmpty()) {
            ConstraintViolation<T> violation = validationSet.iterator().next();
            throw new ValidationException(violation.getMessage());
        }
    }

    /**
     * JSR303校验，分组为默认
     * @param obj 校验对象
     * @param <T>
     */
    public static <T> void validate(T obj) {
        validate(obj, Default.class);
    }

    /**
     * JSR303校验
     * @param obj 校验对象
     * @param group 分组
     * @param <T>
     */
    public static <T> void validateInsert(T obj, Class<?> group) {
        validate(obj, group);
    }
}
