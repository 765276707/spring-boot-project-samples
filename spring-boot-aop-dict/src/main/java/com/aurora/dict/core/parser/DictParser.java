package com.aurora.dict.core.parser;

import com.aurora.dict.core.annotation.DictEntity;
import com.aurora.dict.core.annotation.DictField;
import com.aurora.dict.core.entry.DictEntry;
import com.aurora.dict.core.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 数据字典解析器
 * @author xzbcode
 */
@Component
public class DictParser {

    private Logger logger = LoggerFactory.getLogger(DictParser.class);

    /**
     * 解析数据字典
     * @param result
     * @param dictEntry
     */
    public void parseDictText(Object result, DictEntry dictEntry) {
        if (result != null) {
            DictEntity dictEntity = AnnotationUtils.findAnnotation(result.getClass(), DictEntity.class);
            if (dictEntity != null) {
                // 解析字段
                boolean analysisProperty = dictEntity.analysisProperty();
                Field[] fields = result.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Class<?> fieldType = field.getType();
                    if (!analysisProperty) {
                        // 只解析基础类型字段
                        if (isPrimitive(fieldType)) {
                            // 基础类型及其包装类
                            this.analysisField(result, field, dictEntry);
                        }
                    } else {
                        // 解析全部类型字段
                        if (isPrimitive(fieldType)) {
                            // 基础类型及其包装类
                            this.analysisField(result, field, dictEntry);
                        } else if (isCollection(fieldType)) {
                            // 集合，List、Set
                            Object fieldValue = this.getFieldValue(field.getName(), result);
                            if (fieldValue != null) {
                                Collection collection = (Collection) fieldValue;
                                for (Object obj : collection) {
                                    this.parseDictText(obj, dictEntry);
                                }
                            }
                        } else if (isObjectArray(fieldType)) {
                            // 数组[]
                            Object fieldValue = this.getFieldValue(field.getName(), result);
                            if (fieldValue != null) {
                                Object[] array = (Object[]) fieldValue;
                                for (Object obj : array) {
                                    this.parseDictText(obj, dictEntry);
                                }
                            }
                        } else {
                            // 其它实体类对象Bean
                            Object fieldValue = this.getFieldValue(field.getName(), result);
                            this.parseDictText(fieldValue, dictEntry);
                        }
                    }
                }
            }
        }

    }


    private void analysisField(Object object, Field field, DictEntry dictEntry) {
        DictField dictField = field.getAnnotation(DictField.class);
        // 描述字段必须有@DictField注解，且类型必须为String
        if (dictField!=null && field.getType()==String.class) {
            String code = dictField.code();
            String keyField = dictField.keyFieldName();
            Object keyValue = this.getFieldValue(keyField, object);
            if (keyValue != null) {
                String dictText = this.getDictValue(code, String.valueOf(keyValue), dictEntry);
                // 如果对应字典为空，则采用默认值
                this.setFieldValue(field, object,
                        !StringUtils.isEmpty(dictText)? dictText : dictField.defaultValue());
            }
        }
    }


    private String getDictValue(String code, String key, DictEntry dictEntry) {
        // 获取相应code的字典
        return dictEntry.getItemValueStr(code, key);
    }

    private Object getFieldValue(String fieldName, Object object) {
        try {
            return ReflectUtil.getFieldValue(fieldName, object);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
    }

    private void setFieldValue(Field field, Object object, Object value) {
        try {
            ReflectUtil.setFieldValue(field, object, value);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * 是否为基础类型或其包装类
     * @param clazz
     * @return
     */
    private boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz==String.class
                || Number.class.isAssignableFrom(clazz) || clazz==Boolean.class;
    }

    /**
     * 是否为集合类
     * @param clazz
     * @return
     */
    private boolean isCollection(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 判断是否为实体类数组，非基础类型或其包装类
     * @param clazz
     * @return
     */
    private boolean isObjectArray(Class<?> clazz) {
        return clazz.isArray() && !isPrimitive(clazz.getComponentType());
    }

}
