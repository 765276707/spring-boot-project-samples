package com.aurora.validation.samples.sensitive;

import com.aurora.validation.core.sensitive.ISensitiveWordDataSource;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义文件敏感词数据源
 * @author xzbcode
 */
@Component("sensitiveWordDataSource")
public class FileSensitiveWordDataSource implements ISensitiveWordDataSource {

    // TODO 待添加敏感词数据源
    @Override
    public Map<String, String> getData() {
        Map<String, String> data = new HashMap<>(8);
        return data;
    }

}
