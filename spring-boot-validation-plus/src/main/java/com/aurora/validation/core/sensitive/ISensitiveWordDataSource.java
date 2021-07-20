package com.aurora.validation.core.sensitive;

import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词数据源
 * @author xzbcode
 */
public interface ISensitiveWordDataSource {

    // 获取敏感词数据
    default Map<String, String> getData() {
        return new HashMap<>(1);
    }

}
